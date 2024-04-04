package com.distributed_computing.jpa.service;

import com.distributed_computing.jpa.bean.Tweet;
import com.distributed_computing.jpa.exception.AlreadyExists;
import com.distributed_computing.jpa.exception.IncorrectValuesException;
import com.distributed_computing.jpa.exception.NoSuchTweet;
import com.distributed_computing.jpa.repository.CreatorRepository;
import com.distributed_computing.jpa.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetService {

    final TweetRepository tweetRepository;
    final CreatorRepository creatorRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository, CreatorRepository creatorRepository) {
        this.tweetRepository = tweetRepository;
        this.creatorRepository = creatorRepository;
    }

    public Tweet create(Tweet tweet, int ownerId){
        if(tweetRepository.existsTweetByTitle(tweet.getTitle())) throw new AlreadyExists("Tweet with this title already exists");
        if(!creatorRepository.existsById(ownerId)) throw new IncorrectValuesException("There is no creator with this id");
        tweet.setCreator(creatorRepository.getReferenceById(ownerId));
        tweet.setCreated(LocalDateTime.now());
        tweet.setModified(LocalDateTime.now());
        tweetRepository.save(tweet);

        return tweet;
    }

    public List<Tweet> getAll(){
        return tweetRepository.findAll();
    }

    public Tweet getById(int id){

        return tweetRepository.getReferenceById(id);
    }

    public Tweet update(Tweet tweet){
        if(!tweetRepository.existsById(tweet.getId())) throw new NoSuchTweet("There is no such tweet with this id");
        Tweet prevTweet = tweetRepository.getReferenceById(tweet.getId());
        tweet.setCreated(prevTweet.getCreated());
        tweet.setModified(LocalDateTime.now());
        tweetRepository.save(tweet);
        return tweet;
    }

    public void delete(int id){
        if(!tweetRepository.existsById(id)) throw new NoSuchTweet("There is no such tweet with this id");
        tweetRepository.deleteById(id);
    }
}
