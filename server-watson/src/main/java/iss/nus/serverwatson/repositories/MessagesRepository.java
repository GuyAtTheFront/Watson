package iss.nus.serverwatson.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import iss.nus.serverwatson.models.Message;
import iss.nus.serverwatson.utils.GenericAggregationOperation;

@Repository
public class MessagesRepository {
    
    private final String COLLECTION_MESSAGES = "messages";
    private final String FIELD_BOT_ID = "botId";
    private final String FIELD_MEMBER_ID = "memberId";
    private final String FIELD_UNDERSCORE_ID = "_id";
    private final String FIELD_DATE = "date";


    @Autowired
    MongoTemplate template;

    public void insertMessage(Document messages) {
        template.insert(messages, "messages");
    }

    public List<Message> findLatestMessagesByBotId(Long botId) {
        /*
            db.messages.aggregate([
            {
                $match: {botId: 6127352122}
            }
            ,
            {
                $sort: {date: -1}
            }
            ,
            {
                $group: {_id: '$memberId',  messages: {$push: '$$ROOT'}}
            }
            ,
            {
                $replaceRoot: { newRoot: {$first: '$messages''}}
            }
            ])
         */

        final Integer sortDirection = -1;

        final String MONGO_MATCH_BOT_ID = 
        """
            {
                $match: {botId: %s}
            }
        """.formatted(botId);

        final String MONGO_SORT_DATE = 
        """
            {
                $sort: {date: %s}
            }
        """.formatted(sortDirection);

        final String MONGO_GROUP_MEMBER_ID =
        """
            {
                $group: {
                    _id: '$memberId',  
                    messages: {$push: '$$ROOT'}
                }
            }
        """;

        final String MONGO_REPLACE_ROOT_FIRST_MESSAGE = 
        """
            {
                $replaceRoot: { 
                    newRoot: {$first: '$messages'}
                }
            }
        """;

        AggregationOperation matchBotId = new GenericAggregationOperation(MONGO_MATCH_BOT_ID);
        AggregationOperation sortDate = new GenericAggregationOperation(MONGO_SORT_DATE);
        AggregationOperation groupMemberId = new GenericAggregationOperation(MONGO_GROUP_MEMBER_ID);
        AggregationOperation replaceRootFirstMessage = new GenericAggregationOperation(MONGO_REPLACE_ROOT_FIRST_MESSAGE);

        Aggregation pipeline = Aggregation.newAggregation(matchBotId, sortDate, groupMemberId, replaceRootFirstMessage);

        AggregationResults<Message> res = template.aggregate(pipeline, COLLECTION_MESSAGES, Message.class);

        return res.getMappedResults();
    }

    public List<Message> findMessagesByIdsDescLimit(Long botId, Long memberId, Integer skip, Integer limit) {
        /*
        db.messages.find({memberId: 153628701, botId: 6127352122})
            .sort({date: -1})
            .skip(0)
            .limit(50)
            .sort({date:1}); 
         */

         Criteria criteria = Criteria.where(FIELD_BOT_ID).is(botId).and(FIELD_MEMBER_ID).is(memberId);

        Query query = Query.query(criteria)
                        .with(Sort.by(Sort.Direction.DESC, FIELD_DATE))
                        .skip(skip)
                        .limit(limit)
                        .with(Sort.by(Sort.Direction.ASC, FIELD_DATE));

        query.fields()
        .exclude(FIELD_UNDERSCORE_ID);

        return template.find(query, Message.class, COLLECTION_MESSAGES);
    }

    public List<Message> findMessagesByIdsDescLimit(Long botId, Long memberId) {
        return this.findMessagesByIdsDescLimit(botId, memberId, 0, 50);
    }
}
