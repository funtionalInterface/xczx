package com.xuecheng.search.service.impl;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.domain.search.response.SearchCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.search.service.EsCourseService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EsCourseServiceImpl implements EsCourseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsCourseServiceImpl.class);

    @Value("${xuecheng.elasticsearch.course.index}")
    private String es_index;
    @Value("${xuecheng.elasticsearch.course.type}")
    private String es_type;
    @Value("${xuecheng.elasticsearch.course.source_field}")
    private String source_field;

    @Value("${xuecheng.elasticsearch.media.index}")
    private String media_index;
    @Value("${xuecheng.elasticsearch.media.type}")
    private String media_type;
    @Value("${xuecheng.elasticsearch.media.source_field}")
    private String media_field;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 课程列表搜索
     *
     * @param page              页码
     * @param size              每页数量
     * @param courseSearchParam 搜索参数
     * @return
     * @throws IOException
     */
    public QueryResponseResult<CoursePub> findList(int page, int size, CourseSearchParam courseSearchParam) throws IOException {
        // 设置索引
        SearchRequest searchRequest = new SearchRequest(es_index);
        // 设置类型
        searchRequest.types(es_type);
        // 创建搜索源对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 创建布尔查询对象
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        // 源字段过滤
        String[] fieldArr = source_field.split(",");
        searchSourceBuilder.fetchSource(fieldArr, new String[]{});

        // 根据关键字进行查询
        if (StringUtils.isNotEmpty(courseSearchParam.getKeyword())) {
            // 匹配关键词
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(courseSearchParam.getKeyword(), "name", "teachplan", "description");
            // 设置匹配占比
            multiMatchQueryBuilder.minimumShouldMatch("70%");
            // 提升字段的权重值
            multiMatchQueryBuilder.field("name", 10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }

        // 根据难度进行过滤
        if (StringUtils.isNotEmpty(courseSearchParam.getMt())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt", courseSearchParam.getMt()));
        }
        if (StringUtils.isNotEmpty(courseSearchParam.getSt())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("st", courseSearchParam.getSt()));
        }
        // 根据等级进行过滤
        if (StringUtils.isNotEmpty(courseSearchParam.getGrade())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("grade", courseSearchParam.getGrade()));
        }

        // 设置分页参数
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0 || size > 20) {
            size = 20;
        }
        // 计算搜索起始位置
        int start = (page - 1) * size;
        searchSourceBuilder.from(start);
        searchSourceBuilder.size(size);

        // 将布尔查询对象添加到搜索源内
        searchSourceBuilder.query(boolQueryBuilder);

        // 配置高亮信息
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        // 设置高亮字段
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        searchSourceBuilder.highlighter(highlightBuilder);

        // 请求搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (Exception e) {
            // 搜索异常
            e.printStackTrace();
            LOGGER.error("search error ...{}", e.getMessage());
            return new QueryResponseResult<>(CommonCode.FAIL, null);
        }

        // 结果收集处理
        SearchHits hits = searchResponse.getHits();
        // 获取匹配度高的结果
        SearchHit[] searchHits = hits.getHits();
        // 总记录数
        long totalHits = hits.getTotalHits();
        // 数据列表
        ArrayList<CoursePub> list = new ArrayList<>();

        // 添加数据
        for (SearchHit hit : searchHits) {
            CoursePub coursePub = new CoursePub();

            Map<String, Object> sourceAsMap = hit.getSourceAsMap();


            // 取出id
            String id = (String) sourceAsMap.get("id");
            coursePub.setId(id);
            // 取出名称
            String name = (String) sourceAsMap.get("name");

            // 取出高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields.get("name") != null) {
                HighlightField highlightField = highlightFields.get("name");
                Text[] fragments = highlightField.fragments();
                StringBuilder stringBuffer = new StringBuilder();
                // 拼接字段
                for (Text text : fragments) {
                    stringBuffer.append(text);
                }
                name = stringBuffer.toString();
            }
            coursePub.setName(name);
            // 图片
            String pic = (String) sourceAsMap.get("pic");
            coursePub.setPic(pic);
            // 优惠后的价格
            Double price = null;
            if (sourceAsMap.get("price") != null) {
                price = Double.parseDouble(sourceAsMap.get("price").toString());
            }
            coursePub.setPrice(price);
            // 优惠前的价格
            Double priceOld = null;
            if (sourceAsMap.get("price_old") != null) {
                priceOld = Double.parseDouble(sourceAsMap.get("price_old").toString());
            }
            coursePub.setPrice_old(priceOld);
            list.add(coursePub);


        }

        // 返回响应结果
        QueryResult<CoursePub> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(totalHits);
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据id搜索课程发布信息
     *
     * @param id 课程id
     * @return JSON数据
     */
    public Map<String, CoursePub> getdetail(String id) {
        // 设置索引
        SearchRequest searchRequest = new SearchRequest(es_index);
        // 设置类型
        searchRequest.types(es_type);
        // 创建搜索源对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 设置查询条件,根据id进行查询
        searchSourceBuilder.query(QueryBuilders.termQuery("_id", id));
        // 这里不使用source的原字段过滤,查询所有字段
        // searchSourceBuilder.fetchSource(new String[]{"name", "grade", "charge","pic"}, newString[]{});

        // 设置搜索源对象
        searchRequest.source(searchSourceBuilder);

        // 执行搜索
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取搜索结果
        assert searchResponse != null;
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits(); //获取最优结果
        Map<String, CoursePub> map = new HashMap<>();
        for (SearchHit hit : searchHits) {
            // 从搜索结果中取值并添加到coursePub对象
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String courseId = (String) sourceAsMap.get("id");
            String name = (String) sourceAsMap.get("name");
            String grade = (String) sourceAsMap.get("grade");
            String charge = (String) sourceAsMap.get("charge");
            String pic = (String) sourceAsMap.get("pic");
            String description = (String) sourceAsMap.get("description");
            String teachplan = (String) sourceAsMap.get("teachplan");
            CoursePub coursePub = new CoursePub();
            coursePub.setId(courseId);
            coursePub.setName(name);
            coursePub.setPic(pic);
            coursePub.setGrade(grade);
            coursePub.setCharge(charge);
            coursePub.setTeachplan(teachplan);
            coursePub.setDescription(description);
            // 设置map对象
            map.put(courseId, coursePub);
        }
        return map;
    }

    /**
     * 根据一个或者多个课程计划id查询媒资信息
     *
     * @param teachplanIds 课程id
     * @return QueryResponseResult
     */
    public QueryResponseResult<TeachplanMediaPub> getmedia(String[] teachplanIds) {
        // 设置索引
        SearchRequest searchRequest = new SearchRequest(media_index);

        // 设置类型
        searchRequest.types(media_type);

        // 创建搜索源对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 源字段过滤
        String[] media_index_arr = media_field.split(",");
        searchSourceBuilder.fetchSource(media_index_arr, new String[]{});

        // 查询条件,根据课程计划id查询(可以传入多个课程计划id)
        searchSourceBuilder.query(QueryBuilders.termsQuery("teachplan_id", teachplanIds));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取结果
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        SearchHit[] searchHits = hits.getHits();

        // 数据列表
        List<TeachplanMediaPub> teachplanMediaPubList = new ArrayList<>();

        for (SearchHit hit : searchHits) {
            TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            // 取出课程计划媒资信息
            String courseid = (String) sourceAsMap.get("courseid");
            String media_id = (String) sourceAsMap.get("media_id");
            String media_url = (String) sourceAsMap.get("media_url");
            String teachplan_id = (String) sourceAsMap.get("teachplan_id");
            String media_fileoriginalname = (String) sourceAsMap.get("media_fileoriginalname");
            String timestamp = (String) sourceAsMap.get("timestamp");
            teachplanMediaPub.setCourseId(courseid);
            teachplanMediaPub.setMediaUrl(media_url);
            teachplanMediaPub.setMediaFileOriginalName(media_fileoriginalname);
            teachplanMediaPub.setMediaId(media_id);
            teachplanMediaPub.setTeachplanId(teachplan_id);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            try {
                teachplanMediaPub.setTimestamp(dateFormat.parse(timestamp.replace("Z", "UTC")));
            } catch (ParseException e) {
                e.printStackTrace();
                ExceptionCast.cast(CourseCode.COURSE_MEDIA_DATE_PARSE_ERROR);
            }
            // 将对象加入到列表中
            teachplanMediaPubList.add(teachplanMediaPub);
        }

        // 构建返回课程媒资信息对象
        QueryResult<TeachplanMediaPub> queryResult = new QueryResult<>();
        queryResult.setList(teachplanMediaPubList);
        queryResult.setTotal(totalHits);
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }

    @Override
    public ResponseResult deleteEsCache(String id) {
        // 设置索引
        SearchRequest searchRequest = new SearchRequest("xc_course_media");
        // 设置类型
        searchRequest.types("doc");
        // 创建搜索源对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 查询条件,根据课程计划id查询(可以传入多个课程计划id)
        searchSourceBuilder.query(QueryBuilders.termQuery("media_id", id));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取结果
        assert searchResponse != null;
        SearchHits hits = searchResponse.getHits();
        if (hits==null) return new ResponseResult(SearchCode.ES_CACHE_DELETE_NULL);
        long totalHits = hits.getTotalHits();
        SearchHit[] searchHits = hits.getHits();
        BulkRequest bulkRequest = new BulkRequest();
        // 删除索引请求对象
        // 响应对象
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String teachplan_id = (String) sourceAsMap.get("teachplan_id");
            DeleteRequest deleteRequest = new DeleteRequest("xc_course_media", "doc", teachplan_id);
            bulkRequest.add(deleteRequest);
        }
        try {
            restHighLevelClient.bulk(bulkRequest);
        } catch (IOException e) {
            // 删除失败不进行处理，进行下一步删除
//            e.printStackTrace();
//            ExceptionCast.cast(SearchCode.ES_CACHE_DELETE_ERROR);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
