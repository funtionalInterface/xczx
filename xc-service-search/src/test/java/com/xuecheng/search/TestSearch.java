package com.xuecheng.search;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearch {
    @Autowired
    RestHighLevelClient client;


    /**
     * 搜索type下的全部记录
     */
    @Test
    public void testSearchAll() throws IOException {
        // 获取搜索请求对象，并且设置类型
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 设置搜索方式
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        // 配置source源字段过虑，1显示的，2排除的
        searchSourceBuilder.fetchSource(new String[]{"id", "name", "studymodel"}, new String[]{});

        // 将搜索源配置到搜索请求中，执行搜索，获取搜索响应结果
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);

        // 获取所有搜索结果、总匹配数量
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();

        // 筛选出匹配度高的文档记录
        SearchHit[] searchHits = hits.getHits();

        // 遍历结果
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }

    /**
     * 分页查询
     */
    @Test
    public void testSearchPage() throws IOException {
        // 获取搜索请求对象，并且设置类型
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 设置搜索方式
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        // ES这里是按起始坐标来实现分页查询,所以我们要指定一个页码
        int pageNum = 1;
        int size = 2;
        // 通过页码和查询数量得出起始位置
        int fromNum = (pageNum - 1) * size;
        // 分页查询，设置起始下标，从0开始
        searchSourceBuilder.from(0);
        // 每页显示个数
        searchSourceBuilder.size(size);
        // 配置source源字段过虑，1显示的，2排除的
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel"}, new String[]{});

        // 将搜索源配置到搜索请求中，执行搜索，获取搜索响应结果
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);

        // 获取所有搜索结果、总匹配数量
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();

        // 筛选出匹配度高的文档记录
        SearchHit[] searchHits = hits.getHits();

        // 遍历结果
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }

    /**
     * Term Query 精确查询
     */
    @Test
    public void TestSearchTermQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 设置查询类型为termQuery,精确匹配name中包含spring的文档
        searchSourceBuilder.query(QueryBuilders.termQuery("name", "spring"));//source源字段过虑

        // 不指定过滤条件则默认显示查询到的文档的所有字段
        searchSourceBuilder.fetchSource(new String[]{}, new String[]{});

        // 设置搜索源并获取搜索结果
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);

        // 获取搜索结果
        // .getHits() 取本次所有匹配结果
        // .getHits().getHits() 筛选出匹配度高的文档记录
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        // 筛选匹配度最高的结果
        SearchHit[] searchHits = hits.getHits();

        System.out.println("结果数量为: " + totalHits);
        // 输出搜索结果
        for (SearchHit hit : searchHits) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    /**
     * 多id查询
     */
    @Test
    public void testSearchId() throws IOException {
        // 获取搜索请求对象，并且设置类型
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        String[] split = new String[]{"4028e581617f945f01617f9dabc40000", "297e7c7c62b888f00162b8a965510001"};
        List<String> idList = Arrays.asList(split);
        // 设置搜索方式
        searchSourceBuilder.query((QueryBuilders.termsQuery("_id", idList)));

        // 配置source源字段过虑，1显示的，2排除的
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel"}, new String[]{});

        // 将搜索源配置到搜索请求中，执行搜索，获取搜索响应结果
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);

        // 获取所有搜索结果、总匹配数量
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();

        // 筛选出匹配度高的文档记录
        SearchHit[] searchHits = hits.getHits();

        // 遍历结果
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }

    /**
     * 根据关键字搜索
     *
     * @throws IOException
     */
    @Test
    public void testMatchQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name"}, new String[]{});
        // 设置过滤器，匹配关键字
        searchSourceBuilder.query(QueryBuilders.matchQuery("description", "spring开发").operator(Operator.OR));
        searchRequest.source(searchSourceBuilder);

        // 执行搜索请求
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        // 输出搜索结果
        for (SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
            System.out.println(hit.getSourceAsMap());
        }
    }

    /**
     * 占比搜索
     *
     * @throws IOException
     */
    @Test
    public void testMatchMinimum() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name"}, new String[]{});
        // 设置过滤器，匹配关键字
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("description", "spring开发").minimumShouldMatch("80%");
        searchSourceBuilder.query(matchQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        // 执行搜索请求
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        // 输出搜索结果
        for (SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
            System.out.println(hit.getSourceAsMap());
        }
    }

    /**
     * 匹配多个字段并提高权重搜索
     *
     * @throws IOException
     */
    @Test
    public void testMatchMinimums() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name"}, new String[]{});
        // 设置过滤器，匹配关键字
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring框架", "name", "description")
                .minimumShouldMatch("50%");
        multiMatchQueryBuilder.field("name", 10); // 提升boost
        searchSourceBuilder.query(multiMatchQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        // 执行搜索请求
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        // 输出搜索结果
        for (SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
            System.out.println(hit.getSourceAsMap());
        }
    }

    // BoolQuery，将搜索关键字分词，拿分词去索引库搜索
    @Test
    public void testBoolQuery() throws IOException {
        // 创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        // 创建搜索源配置对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(new String[]{"name", "pic", "studymodel"}, new String[]{});
        // multiQuery
        String keyword = "spring开发框架";
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring框架",
                "name", "description")
                .minimumShouldMatch("50%");
        multiMatchQueryBuilder.field("name", 10);
        // TermQuery
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "201001");
        // 布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        boolQueryBuilder.must(termQueryBuilder);
        // 设置布尔查询对象
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        // 设置搜索源配置
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println(hit.getSourceAsString());
            System.out.println(sourceAsMap);
        }
    }

    // BoolQuery，将搜索关键字分词，拿分词去索引库搜索,过滤器过滤结果
    @Test
    public void testBoolQueryFilter() throws IOException {
        // 创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        // 创建搜索源配置对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(new String[]{"name", "pic", "studymodel"}, new String[]{});
        // multiQuery
        String keyword = "spring开发框架";
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring框架",
                "name", "description")
                .minimumShouldMatch("50%");
        multiMatchQueryBuilder.field("name", 10);
        // TermQuery
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "201001");
        // 布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        boolQueryBuilder.must(termQueryBuilder);
        // 过虑条件
        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel", "201001"));
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(0).lte(100));
        // 设置布尔查询对象
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);


        // 设置搜索源配置
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        long totalHits = hits.getTotalHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println(hit.getSourceAsString());
            System.out.println(sourceAsMap);
        }
    }

    @Test
    public void testSort() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{
                "name", "studymodel", "price", "description"},
                new String[]{});
        searchRequest.source(searchSourceBuilder);
        // 布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 过虑
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(0).lte(100));
        //排序
        searchSourceBuilder.sort(new FieldSortBuilder("studymodel").order(SortOrder.DESC));
        searchSourceBuilder.sort(new FieldSortBuilder("price").order(SortOrder.ASC));
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    /**
     * 高亮显示
     * @throws IOException
     */
    @Test
    public void testHighlight() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","description"},
                new String[]{});
        searchRequest.source(searchSourceBuilder);
        //匹配关键字
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("开发",
                "name", "description");
        searchSourceBuilder.query(multiMatchQueryBuilder);
        //布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(searchSourceBuilder.query());
        //过虑
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(0).lte(100));
        //排序
        searchSourceBuilder.sort(new FieldSortBuilder("studymodel").order(SortOrder.DESC));
        searchSourceBuilder.sort(new FieldSortBuilder("price").order(SortOrder.ASC));
        //高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<tag>");//设置前缀
        highlightBuilder.postTags("</tag>");//设置后缀
        // 设置高亮字段
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        // highlightBuilder.fields().add(new HighlightBuilder.Field("description"));

        searchSourceBuilder.highlighter(highlightBuilder);
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //名称
            String name = (String) sourceAsMap.get("name");
            //取出高亮字段内容
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if(highlightFields!=null){
                HighlightField nameField = highlightFields.get("name");
                if(nameField!=null){
                    Text[] fragments = nameField.getFragments();
                    StringBuilder stringBuffer = new StringBuilder();
                    for (Text str : fragments) {
                        stringBuffer.append(str.string());
                    }
                    name = stringBuffer.toString();
                }
            }
            //取出所有结果
            sourceAsMap = hit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }
}
