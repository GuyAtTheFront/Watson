package iss.nus.serverwatson.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.nus.serverwatson.models.MemberDetail;
import iss.nus.serverwatson.services.MemberDetailService;

@RestController
@RequestMapping(path="/api/members", produces=MediaType.APPLICATION_JSON_VALUE)
public class BotMemberController {
    
    @Autowired
    MemberDetailService memberDetailSvc;

    @GetMapping(path="/{botId}")
    public List<MemberDetail> getMemberSummaries(@PathVariable Long botId) {
        return memberDetailSvc.getDetailsByBotId(botId);
    }




}
