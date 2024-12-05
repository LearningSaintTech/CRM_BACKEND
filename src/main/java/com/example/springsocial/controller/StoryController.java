//last changes in 20/11/24
package com.example.springsocial.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springsocial.DTO.StoryDTO;
import com.example.springsocial.DTO.UserStoriesDTO;
import com.example.springsocial.model.Story;
import com.example.springsocial.model.User;
import com.example.springsocial.services.StoryService;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api/stories")
public class StoryController {
    private final StoryService storyService;
    
    private final User user;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
		this.user = new User();
    }
    
    
    //Use in Admin side only
    @GetMapping("all")
    public List<Story> getAllstories(){
    	System.out.println("get stories nearby");
        return storyService.getAllStories();
    }

    // Method to create multiple stories at once
    @PostMapping("/bulk")
    public List<Story> createStories(@RequestParam("titles") List<String> titles,
                                     @RequestParam("files") List<MultipartFile> files, @RequestParam("userId") User userId, @RequestParam("latitude") double latitude,@RequestParam("longitude") double longitude) throws IOException, java.io.IOException {
    	System.out.println("Hellloooooo bhaag" + titles + files +"and :"+latitude+longitude);
        // Ensure both lists have the same size
        if (titles.size() != files.size()) {
        	System.out.println("hellooooo true ho gya");
            throw new IllegalArgumentException("Titles and files must have the same number of entries.");
        }
        System.out.println("Hello this could be true");
        return storyService.createStories(titles, files, userId, latitude, longitude);  // Pass to service to handle bulk creation
    }


    //Show stories within lat & long 
    @GetMapping("/nearby")
    public List<UserStoriesDTO> getGroupedStoriesWithinRadius(@RequestParam Double latitude,
                                                              @RequestParam Double longitude,
                                                              @RequestParam Double radius) {
        // Call the service to get grouped stories
        return storyService.getGroupedStoriesWithinRadius(latitude, longitude, radius);
    }


    @DeleteMapping("/story/{id}")
    public ResponseEntity<String> deleteStoriesById(@PathVariable Long id) {
    	System.out.println("running in delete method>>>:"+id);
        storyService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }
    
    @GetMapping("/story/{id}")
    public ResponseEntity<User> showStoriesById(@PathVariable Long id) {
    	System.out.println("showing story by idsss>>>:"+id);
        return ResponseEntity.ok(storyService.getStoryById(id));
    }
    
    //Delete stories by user ID
    //20/11/24
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteStoriesByUserId(@PathVariable Long userId) {
    	System.out.println("running in delete method>>>:"+userId);
        storyService.deleteStoriesByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    //get story by userId
    //20/11/24
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Story>> getStoriesByUserId(@PathVariable Long userId) {
        List<Story> stories = storyService.getStoriesByUserId(userId);
        return ResponseEntity.ok(stories);
    }
        
}
