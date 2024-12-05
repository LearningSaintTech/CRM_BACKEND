//last changes 20/11/24
package com.example.springsocial.services;


import com.example.springsocial.DTO.StoryDTO;
import com.example.springsocial.DTO.UserStoriesDTO;
import com.example.springsocial.exception.StoryNotFoundException;
import com.example.springsocial.model.Story;
import com.example.springsocial.model.User;
import com.example.springsocial.model.UserProfile;
import com.example.springsocial.repository.StoryRepository;
import com.example.springsocial.repository.StoryViewRepository;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoryService {

    private  final Double latitude = null;
	private  final Double longitude = null;
	
	private   StoryRepository storyRepository;
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StoryViewRepository storyViewRepository;
	   
	   @Autowired
	    public StoryService(StoryRepository storyRepository, UserRepository userRepository,StoryViewRepository storyViewRepository) {
	        this.storyRepository = storyRepository;
	        this.userRepository = userRepository;
	        this.storyViewRepository = storyViewRepository;
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
    public User getStoryById(Long id) {
    	System.out.println("fetching story by id>>>>>>>>>>");
        return userRepository.findById(id)
                .orElseThrow(() -> new StoryNotFoundException("Story not found with ID: " + id));
    }


    
    public List<UserStoriesDTO> getGroupedStoriesWithinRadius(Double latitude, Double longitude, Double radius) {
        List<Story> stories = storyRepository.findStoriesWithinRadiusAndNotExpired(latitude, longitude, radius);

        // Group stories by userId
        Map<Long, List<Story>> groupedStories = stories.stream()
                .collect(Collectors.groupingBy(story -> story.getUser().getId()));

        // Convert grouped stories into UserStoriesDTO
        return groupedStories.entrySet().stream()
                .map(entry -> {
                    Long userId = entry.getKey();
                    List<Story> userStories = entry.getValue();
                    String userName = userStories.get(0).getUser().getName(); // All stories have the same user

                    // Map each Story to StoryDTO
                    List<StoryDTO> storyDTOs = userStories.stream()
                            .map(story -> new StoryDTO(
                                    story.getId(),
                                    story.getTitle(),
                                    story.getLatitude(),
                                    story.getLongitude(),
                                    story.getFile(),
                                    userId,
                                    userName
                            ))
                            .collect(Collectors.toList());

                    return new UserStoriesDTO(userId, userName, storyDTOs);
                })
                .collect(Collectors.toList());
    }

    
//    public List<Story> getAllStoriesByUserIdWithinRadiusAndNotExpired(Long userId, Double latitude, Double longitude, Double radius) {
//    	System.out.println("calling in story service class>>>>>>>>>>>>>>>");
//        return storyRepository.findAllStoriesByUserIdWithinRadiusAndNotExpired(userId, latitude, longitude, radius);
//    }


    public void deleteStoriesByUserId(Long userId) {
        storyRepository.deleteByUserId(userId);
    }


    //delete by story id's
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		System.out.println("delet by story id");
		storyViewRepository.deleteByStoryId(id);
		storyRepository.deleteById(id);
	}
	
	//get story by user id 
	public List<Story> getStoriesByUserId(Long userId) {
	    return storyRepository.findStoriesByUserId(userId);
	}
}

