package org.irruptiondays.genealogy.model;

import org.irruptiondays.genealogy.GenprojApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Tests the person page model.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GenprojApplication.class)
@WebAppConfiguration
@Transactional
public class PersonPageModelTest {

    @Test
    public void testGetFileName() {
        PersonPageModel personPageModel = PersonPageModel.builder().firstName("George")
                .middleNames("Henry Adam")
                .lastName("O'Brien")
                .maidenName("G")
                .suffix("Jr.")
                .id(1L).build();
        assertEquals("FileName was " + personPageModel.getFileName(), "1-obrien-george-henry-adam-g-jr", personPageModel.getFileName());
    }
}
