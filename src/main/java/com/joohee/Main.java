package com.joohee;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("memberA");
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);

            Member findMember = em.find(Member.class, 2L);
            findMember.setUsername("fffaa");
            System.out.println("findMember.Username = " + findMember.getUsername());

            em.flush();
            em.clear();
            Member findMember2 = em.find(Member.class, 2L);
            System.out.println("findMember.Username2 = " + findMember2.getUsername());
            String query = "select m from Member m, Team t where m.username =  t.name";

            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            System.out.println("result = " + result.size());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}