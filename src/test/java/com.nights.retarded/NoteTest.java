package com.nights.retarded;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.model.Note;
import com.nights.retarded.notes.service.NoteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class NoteTest {

    @Autowired
    private NoteService noteService;

    @Test
    public void getCurrNote(){
        Note note = noteService.getCurrNote("opxLy5O-swOwMfmfQz5tPmbQeRUQ");
        System.out.println(JsonUtils.objectToJson(note));

    }

}
