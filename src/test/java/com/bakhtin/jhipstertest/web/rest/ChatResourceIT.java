package com.bakhtin.jhipstertest.web.rest;

import com.bakhtin.jhipstertest.JhipsterSampleApplicationApp;
import com.bakhtin.jhipstertest.domain.Chat;
import com.bakhtin.jhipstertest.repository.ChatRepository;
import com.bakhtin.jhipstertest.repository.search.ChatSearchRepository;
import com.bakhtin.jhipstertest.service.ChatService;
import com.bakhtin.jhipstertest.service.dto.ChatDTO;
import com.bakhtin.jhipstertest.service.mapper.ChatMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ChatResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChatResourceIT {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private ChatService chatService;

    /**
     * This repository is mocked in the com.bakhtin.jhipstertest.repository.search test package.
     *
     * @see com.bakhtin.jhipstertest.repository.search.ChatSearchRepositoryMockConfiguration
     */
    @Autowired
    private ChatSearchRepository mockChatSearchRepository;

    @Autowired
    private MockMvc restChatMockMvc;

    private Chat chat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chat createEntity() {
        Chat chat = new Chat();
        return chat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chat createUpdatedEntity() {
        Chat chat = new Chat();
        return chat;
    }

    @BeforeEach
    public void initTest() {
        chatRepository.deleteAll();
        chat = createEntity();
    }

    @Test
    public void createChat() throws Exception {
        int databaseSizeBeforeCreate = chatRepository.findAll().size();
        // Create the Chat
        ChatDTO chatDTO = chatMapper.toDto(chat);
        restChatMockMvc.perform(post("/api/chats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isCreated());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeCreate + 1);
        Chat testChat = chatList.get(chatList.size() - 1);

        // Validate the Chat in Elasticsearch
        verify(mockChatSearchRepository, times(1)).save(testChat);
    }

    @Test
    public void createChatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chatRepository.findAll().size();

        // Create the Chat with an existing ID
        chat.setId("existing_id");
        ChatDTO chatDTO = chatMapper.toDto(chat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChatMockMvc.perform(post("/api/chats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeCreate);

        // Validate the Chat in Elasticsearch
        verify(mockChatSearchRepository, times(0)).save(chat);
    }


    @Test
    public void getAllChats() throws Exception {
        // Initialize the database
        chatRepository.save(chat);

        // Get all the chatList
        restChatMockMvc.perform(get("/api/chats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chat.getId())));
    }
    
    @Test
    public void getChat() throws Exception {
        // Initialize the database
        chatRepository.save(chat);

        // Get the chat
        restChatMockMvc.perform(get("/api/chats/{id}", chat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chat.getId()));
    }
    @Test
    public void getNonExistingChat() throws Exception {
        // Get the chat
        restChatMockMvc.perform(get("/api/chats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateChat() throws Exception {
        // Initialize the database
        chatRepository.save(chat);

        int databaseSizeBeforeUpdate = chatRepository.findAll().size();

        // Update the chat
        Chat updatedChat = chatRepository.findById(chat.getId()).get();
        ChatDTO chatDTO = chatMapper.toDto(updatedChat);

        restChatMockMvc.perform(put("/api/chats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isOk());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
        Chat testChat = chatList.get(chatList.size() - 1);

        // Validate the Chat in Elasticsearch
        verify(mockChatSearchRepository, times(1)).save(testChat);
    }

    @Test
    public void updateNonExistingChat() throws Exception {
        int databaseSizeBeforeUpdate = chatRepository.findAll().size();

        // Create the Chat
        ChatDTO chatDTO = chatMapper.toDto(chat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatMockMvc.perform(put("/api/chats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Chat in Elasticsearch
        verify(mockChatSearchRepository, times(0)).save(chat);
    }

    @Test
    public void deleteChat() throws Exception {
        // Initialize the database
        chatRepository.save(chat);

        int databaseSizeBeforeDelete = chatRepository.findAll().size();

        // Delete the chat
        restChatMockMvc.perform(delete("/api/chats/{id}", chat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Chat in Elasticsearch
        verify(mockChatSearchRepository, times(1)).deleteById(chat.getId());
    }

    @Test
    public void searchChat() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        chatRepository.save(chat);
        when(mockChatSearchRepository.search(queryStringQuery("id:" + chat.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(chat), PageRequest.of(0, 1), 1));

        // Search the chat
        restChatMockMvc.perform(get("/api/_search/chats?query=id:" + chat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chat.getId())));
    }
}
