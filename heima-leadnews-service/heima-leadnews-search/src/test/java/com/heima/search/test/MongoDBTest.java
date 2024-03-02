package com.heima.search.test;


import com.heima.model.search.pojos.ApAssociateWords;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootTest
public class MongoDBTest {


    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void testFind(){
        System.out.println(mongoTemplate.find(Query.query(Criteria.where("associateWords").is("黑马程序员")), ApAssociateWords.class));
    }
}
