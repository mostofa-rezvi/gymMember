package com.mostofa.gym.gymMember.gym.service;

import com.mostofa.gym.gymMember.gym.dto.ChartData;
import com.mostofa.gym.gymMember.gym.entity.Member;
import com.mostofa.gym.gymMember.gym.entity.Payment;
import com.mostofa.gym.gymMember.gym.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member memberDetails) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + id));

        member.setName(memberDetails.getName());
        member.setEmail(memberDetails.getEmail());
        member.setDob(memberDetails.getDob());
        member.setJoinDate(memberDetails.getJoinDate());
        member.setMembershipType(memberDetails.getMembershipType());
        member.setServices(memberDetails.getServices());

        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new EntityNotFoundException("Member not found with id: " + id);
        }
        memberRepository.deleteById(id);
    }

    public Member addPayment(Long memberId, Payment payment) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));
        payment.setMember(member);
        member.getPayments().add(payment);
        return memberRepository.save(member);
    }

    public List<ChartData> getMembersByType() {
        return memberRepository.countMembersByMembershipType();
    }

    public Map<String, List<ChartData>> getMonthlyRevenue() {
        List<ChartData> revenueData = memberRepository.calculateMonthlyRevenue();
        // The structure ngx-charts expects is { name: 'Revenue', series: [...] }
        return Map.of("series", revenueData);
    }

}
