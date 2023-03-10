package com.joohee;

import javax.persistence.*;
import java.time.LocalDate;
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

            LocalDate currentDate = LocalDate.now();
            System.out.println("currentDate = " + currentDate.getDayOfMonth());
            Member member = new Member();
            member.setUsername("memberA");
            member.setAge(10);
            member.setTeam(team);

            em.persist(member);

//            Member findMember = em.find(Member.class, 2L);
//            findMember.setUsername("fffaa");
//            Team findTeam = findMember.getTeam();
//            System.out.println("findMember.Username = " + findMember.getUsername());
//            System.out.println("findTeam team = " + findTeam);

            team.getMembers().add(member);
//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();
//            Member findMember2 = em.find(Member.class, 2L);
//            System.out.println("findMember.Username2 = " + findMember2.getUsername());
//            List<Member> members = member.getTeam().getMembers();

            for(Member m : members) {
                System.out.println("m = " + m.getUsername());
            }
//            String query = "select m from Member m, Team t where m.username =  t.name";
//
//            List<Member> result = em.createQuery(query, Member.class)
//                    .getResultList();
//
//            System.out.println("result = " + result.size());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}