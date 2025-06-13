package com.mostofa.gym.gymMember.gym.service;

import com.mostofa.gym.gymMember.exception.ResourceNotFoundException;
import com.mostofa.gym.gymMember.gym.dto.PaymentDTO;
import com.mostofa.gym.gymMember.gym.entity.Member;
import com.mostofa.gym.gymMember.gym.entity.Payment;
import com.mostofa.gym.gymMember.gym.repository.MemberRepository;
import com.mostofa.gym.gymMember.gym.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MemberRepository memberRepository;

    // Convert Entity to DTO
    private PaymentDTO convertToDto(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setDate(payment.getDate());
        dto.setMemberId(payment.getMember().getId());
        return dto;
    }

    @Transactional
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        return convertToDto(payment);
    }

    @Transactional
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Member member = memberRepository.findById(paymentDTO.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + paymentDTO.getMemberId()));

        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setDate(paymentDTO.getDate());
        payment.setMember(member);

        Payment savedPayment = paymentRepository.save(payment);
        return convertToDto(savedPayment);
    }

    @Transactional
    public PaymentDTO updatePayment(Long id, PaymentDTO paymentDetails) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));

        Member member = memberRepository.findById(paymentDetails.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + paymentDetails.getMemberId()));

        payment.setAmount(paymentDetails.getAmount());
        payment.setDate(paymentDetails.getDate());
        payment.setMember(member);

        Payment updatedPayment = paymentRepository.save(payment);
        return convertToDto(updatedPayment);
    }

    @Transactional
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        paymentRepository.delete(payment);
    }
}
