package org.irruptiondays.genealogy.service;

import org.irruptiondays.genealogy.GenprojApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests the FileService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GenprojApplication.class)
@WebAppConfiguration
@Transactional
public class FileServiceTest {

    @Test
    public void testGetAllPersonsAsPersonPageModels() {
        // TODO
    }
}
