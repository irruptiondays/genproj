package org.irruptiondays.genealogy.controller;

import lombok.extern.slf4j.Slf4j;
import org.irruptiondays.genealogy.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Rest endpoints for html file generation.
 */
@Controller
@RequestMapping(value = {"/file", "/file/" })
@Slf4j
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * Get all persons regardless of family branch
     * @return List of persons
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public boolean getAllPersons() {
        try {
            fileService.generateProject();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
