package com.example.demo.service;

import com.example.demo.model.MovieEntity;
import com.example.demo.model.MovieReviewEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.UserType;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.MovieReviewRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.requests.MovieReviewAddRequest;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieReviewService {

    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private MovieReviewRepository movieReviewRepository;

    private static  final long N = 3l;

    private static final long TOP = 3;

    public MovieReviewService(UserRepository userRepository, MovieRepository movieRepository, MovieReviewRepository movieReviewRepository){
        this.userRepository=userRepository;
        this.movieRepository=movieRepository;
        this.movieReviewRepository=movieReviewRepository;
    }

    public boolean addMovieReview(MovieReviewAddRequest movieReviewAddRequest){

        try{
            UserEntity userEntity = userRepository.findById(movieReviewAddRequest.getUserId()).get();
            MovieEntity movieEntity = movieRepository.findById(movieReviewAddRequest.getMovieId()).get();
            long reviewCount = movieReviewRepository.countAllByUserEntity(userEntity);
            updateUserType(userEntity,reviewCount);
            MovieReviewEntity movieReviewEntity = new MovieReviewEntity();
            movieReviewEntity.setMovieEntity(movieEntity);
            movieReviewEntity.setUserEntity(userEntity);
            movieReviewEntity.setReview(movieReviewAddRequest.getReview());
            movieReviewRepository.save(movieReviewEntity);
        }
        catch (Exception e){
            log.debug("Failed to add the movie review for the userId :" + movieReviewAddRequest.getUserId());
            return false;
        }
        return  true;
    }

    public boolean updateMovieReview(MovieReviewAddRequest movieReviewAddRequest){

        try{
            UserEntity userEntity = userRepository.findById(movieReviewAddRequest.getUserId()).get();
            MovieEntity movieEntity = movieRepository.findById(movieReviewAddRequest.getMovieId()).get();
            MovieReviewEntity movieReviewEntity = movieReviewRepository.findAllByUserEntityAndMovieEntity(userEntity,movieEntity);
            movieReviewEntity.setReview(movieReviewAddRequest.getReview());
            movieReviewRepository.save(movieReviewEntity);
        }
        catch (Exception e){
            log.debug("Failed to update the movie review for the userId :" + movieReviewAddRequest.getUserId());
            return false;
        }
        return  true;
    }

    public boolean deleteMovieReview(Integer id){
        try{
            MovieReviewEntity movieReviewEntity = movieReviewRepository.findById(id).get();
            UserEntity userEntity = movieReviewEntity.getUserEntity();
            long reviewCount = movieReviewRepository.countAllByUserEntity(userEntity);
            updateUserType(userEntity,reviewCount);
            movieReviewRepository.delete(movieReviewEntity);
        }
        catch (Exception e){
            log.error("Failed to delete the review :");
            return false;
        }
        return true;
    }

    public List<MovieReviewEntity> listReviewsForAUser(Integer id){
        UserEntity userEntity = userRepository.findById(id).get();
        List<MovieReviewEntity> list = movieReviewRepository.findAllByUserEntity(userEntity);
        return list;

    }

    public List<MovieEntity> findAllMovieRevieEntityByGenre(String genre){
        List<MovieEntity> movieEntities = movieRepository.findAllByGenre(genre);
        Map<Integer,Integer> movieMap = new HashMap<>();
        for (MovieEntity movieEntity : movieEntities){
            List<MovieReviewEntity> movieReviewEntities = movieReviewRepository.findAllByMovieEntity(movieEntity);
            Integer rating = 0;
            Integer count =0;
            for (MovieReviewEntity movieReviewEntity : movieReviewEntities){
                String userType = movieReviewEntity.getUserEntity().getType();
                Integer userRating = userType.equalsIgnoreCase(UserType.CRITIC.name()) ? movieReviewEntity.getReview() *2 : movieReviewEntity.getReview();
                rating = rating + userRating;
                count = count + 1;
            }
            rating = rating/count;
            movieMap.put(movieEntity.getId(),rating);
        }

        Map<Integer,Integer> finalList = sortByValue(movieMap,false);
        System.out.println(finalList);
        List<Integer> list = new ArrayList<>(finalList.keySet());


        List<MovieEntity> movieReviewEntities = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            if (i < TOP) {
                MovieEntity movieEntity = movieRepository.findById(list.get(i)).get();
                movieReviewEntities.add(movieEntity);
            }
        }
        return movieEntities;
    }

    private void updateUserType(UserEntity userEntity, long reviewCount){

        if (reviewCount> N){
            userEntity.setType(UserType.CRITIC.name());
            userRepository.save(userEntity);
            return;
        }
        else if (reviewCount <=N) {
            userEntity.setType(UserType.VIEWER.name());
            userRepository.save(userEntity);
            return;
        }
    }


    private static Map<Integer, Integer> sortByValue(Map<Integer, Integer> unsortMap, final boolean order)
    {
        List<Map.Entry<Integer, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }
}
