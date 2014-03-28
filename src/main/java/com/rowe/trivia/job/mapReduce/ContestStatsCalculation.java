package com.rowe.trivia.job.mapReduce;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.mapreduce.MapReduceJob;
import com.google.appengine.tools.mapreduce.MapReduceSettings;
import com.google.appengine.tools.mapreduce.MapReduceSpecification;
import com.google.appengine.tools.mapreduce.Mapper;
import com.google.appengine.tools.mapreduce.Marshaller;
import com.google.appengine.tools.mapreduce.Marshallers;
import com.google.appengine.tools.mapreduce.Reducer;
import com.google.appengine.tools.mapreduce.ReducerInput;
import com.google.appengine.tools.mapreduce.inputs.DatastoreInput;
import com.google.appengine.tools.mapreduce.outputs.DatastoreOutput;
import com.googlecode.objectify.LoadResult;
import com.googlecode.objectify.ObjectifyService;
import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.UserQuestion;

public class ContestStatsCalculation {
	private static Logger logger = LoggerFactory.getLogger(ContestStatsCalculation.class);

	public static class ContestStats implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private int asked;
		private int answered;
		private int correct;
		
		public ContestStats(UserQuestion userQuestion) {
			asked = 1;
			answered = userQuestion.isAnswered() ? 1 : 0;
			correct = userQuestion.isCorrect() ? 1 : 0;
		}
		public ContestStats() {
		}
		
		public void applyStats(ContestStats otherStats){
			asked += otherStats.asked;
			answered += otherStats.answered;
			correct += otherStats.correct;
		}
		@Override
		public String toString() {
			return "ContestStats [asked=" + asked + ", answered=" + answered
					+ ", correct=" + correct + "]";
		}
	}
	public static class ContestStatsMapper extends Mapper<Entity, String, ContestStats> {
		private static final long serialVersionUID = 1L;

		@Override
		public void map(Entity entity) {
			UserQuestion uq = ObjectifyService.ofy().load().fromEntity(entity);
			emit(uq.getContest().getContestId(), new ContestStats(uq));
		}
		
	}
	
	public static class ContestStatsReducer extends Reducer<String, ContestStats, Entity> {
		private static final long serialVersionUID = 1L;

		@Override
		public void reduce(String key, ReducerInput<ContestStats> stats) {
			ContestStats agg = new ContestStats();
			while(stats.hasNext()){
				agg.applyStats(stats.next());
			}
			logger.info("Stats: {}: {}", key, agg);
			
			/*
			 * if an entity is created here and saved it nulls out all other fields
			 */
			Contest contest = ObjectifyService.ofy().load().type(Contest.class).id(key).now();
			contest.setStats(agg.asked, agg.answered, agg.correct);
			emit(ObjectifyService.ofy().save().toEntity(contest));
		}
		
	}
	
	public static String start(){
		Marshaller<String> keyMarshaller = Marshallers.getSerializationMarshaller();
		Marshaller<ContestStats> statsMarshaller = Marshallers.getSerializationMarshaller();
		MapReduceSpecification<Entity, String, ContestStats, Entity, Void> spec = MapReduceSpecification.of(
			"Contest Stats Calculation", 
			new DatastoreInput(UserQuestion.class.getSimpleName(), 5), 
			new ContestStatsMapper(), 
			keyMarshaller, 
			statsMarshaller, 
			new ContestStatsReducer(), 
			new DatastoreOutput(5)
		);
		
		MapReduceSettings settings = new MapReduceSettings();
		String pipeline = MapReduceJob.start(spec, settings);
		logger.info("Job started: {}", pipeline);
		return pipeline;
	}
}
