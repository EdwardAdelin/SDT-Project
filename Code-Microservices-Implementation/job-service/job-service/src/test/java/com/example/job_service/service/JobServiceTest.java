package com.example.job_service.service;


import com.example.job_service.client.UserClient;
import com.example.job_service.dto.UserDTO;
import com.example.job_service.model.Job;
import com.example.job_service.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateJob_Success() {
        // Mock User Service response
        UserDTO mockClient = new UserDTO();
        mockClient.setId(1L);
        mockClient.setRole("CLIENT");
        when(userClient.getUserById(1L)).thenReturn(mockClient);

        // Mock Repository save
        Job job = new Job();
        job.setTitle("New Job");
        job.setClientId(1L);

        when(jobRepository.save(any(Job.class))).thenReturn(job);

        Job createdJob = jobService.createJob(job);

        assertNotNull(createdJob);
        assertEquals("OPEN", createdJob.getStatus());
        verify(userClient, times(1)).getUserById(1L);
        verify(jobRepository, times(1)).save(job);
    }

    @Test
    void testCreateJob_Failure_WrongRole() {
        // Mock User Service response for a FREELANCER
        UserDTO mockFreelancer = new UserDTO();
        mockFreelancer.setId(2L);
        mockFreelancer.setRole("FREELANCER");
        when(userClient.getUserById(2L)).thenReturn(mockFreelancer);

        Job job = new Job();
        job.setTitle("Bad Job");
        job.setClientId(2L);

        // Expect Exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            jobService.createJob(job);
        });

        assertEquals("Only users with role CLIENT can post jobs!", exception.getMessage());
        verify(jobRepository, never()).save(any(Job.class));
    }
}