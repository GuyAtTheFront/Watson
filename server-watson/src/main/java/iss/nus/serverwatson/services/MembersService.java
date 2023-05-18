package iss.nus.serverwatson.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.nus.serverwatson.models.Member;
import iss.nus.serverwatson.repositories.MembersRepository;

@Service
public class MembersService {

    @Autowired
    MembersRepository memberRepo;
    
    public List<Member> findMembers() {
        return memberRepo.findMembers();
    }
    
    public Boolean addMember(Member member) {
        if(memberRepo.memberExists(member.getId())) {
            return false;
        }

        return memberRepo.insertMember(member);
    }

    public Boolean deleteMember(Long id) {
        if(!memberRepo.memberExists(id)) {
            return false;
        }

        return memberRepo.deleteMember(id);
    }

    public Optional<Member> findMemberById(Long id) {
        if(!memberRepo.memberExists(id)) {
            return Optional.empty();
        }

        return Optional.of(memberRepo.findMemberById(id));
    }


}
