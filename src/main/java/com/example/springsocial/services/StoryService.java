package com.example.springsocial.services;


import com.example.springsocial.model.Story;
import com.example.springsocial.model.User;
import com.example.springsocial.model.UserProfile;
import com.example.springsocial.repository.StoryRepository;
import com.example.springsocial.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoryService {

    private  final Double latitude = null;
	private  final Double longitude = null;
	
	private   StoryRepository storyRepository;
	
	
	@Autowired
	private UserRepository userRepository;
	   
	   @Autowired
	    public StoryService(StoryRepository storyRepository, UserRepository userRepository) {
	        this.storyRepository = storyRepository;
	        this.userRepository = userRepository;
	    }
    

	   public List<Story> createStories(List<String> titles, List<MultipartFile> files, User user, double latitude, double longitude) throws IOException {
	        List<Story> stories = new ArrayList<>();
	        for (int i = 0; i < titles.size(); i++) {
	            Story story = new Story();
	            story.setTitle(titles.get(i));
	            story.setFile(files.get(i).getBytes());
	            story.setUser(user);
	            story.setLatitude(latitude);
	            story.setLongitude(longitude);
	            story.setCreatedAt(LocalDateTime.now());
	            story.setExpirationAt(LocalDateTime.now().plusHours(24)); // Example: expires in 24 hours
	            stories.add(storyRepository.save(story));
	        }
	        return stories;
	    }

    // Method to get all stories
    public List<Story> getAllStories() {
    	System.out.println("get all stories in service class" );
        return storyRepository.findAll();
    }
    
    // Method to get a story by its ID
    public Story getStoryById(Long id) {
        // Use Optional to safely handle the case where the story might not be found
        Optional<Story> story = storyRepository.findById(id);
        System.out.println("Story by id is here" + story);
        return story.orElseThrow(() -> new RuntimeException("Story not found with ID: " + id));
    }


    
    public List<Story> getStoriesWithinRadius(Double latitude, Double longitude, Double radius) {
    	System.out.println("calling in service>>>>>>>>>>>>>>>>>>"+storyRepository.findStoriesWithinRadiusAndNotExpired(latitude, longitude, radius));
        return storyRepository.findStoriesWithinRadiusAndNotExpired(latitude, longitude, radius);
    }
    
    public List<Story> getAllStoriesByUserIdWithinRadiusAndNotExpired(Long userId, Double latitude, Double longitude, Double radius) {
    	System.out.println("calling in story service class>>>>>>>>>>>>>>>");
        return storyRepository.findAllStoriesByUserIdWithinRadiusAndNotExpired(userId, latitude, longitude, radius);
    }


    public void deleteStoriesByUserId(Long userId) {
        storyRepository.deleteByUserId(userId);
    }


	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		System.out.println("delet by story id");
		storyRepository.deleteById(id);
	}
	


}

