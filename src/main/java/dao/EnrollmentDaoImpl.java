package dao;

import entity.Course;
import entity.Enrollment;
import entity.Student;

import javax.persistence.EntityManager;
import java.util.List;

public class EnrollmentDaoImpl implements EnrollmentDao{

    @Override
    public Enrollment getEnrollment(Integer enrollmentId) {
        EntityManager em = Connector.getEmf().createEntityManager();
        Enrollment enrollment = em.find(Enrollment.class, enrollmentId);
        em.close();
        return enrollment;
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        EntityManager em = Connector.getEmf().createEntityManager();
        List<Enrollment> enrollments = em
                .createQuery("Select e from Enrollment e", Enrollment.class)
                .getResultList();

        em.close();
        return enrollments;
    }

    @Override
    public void addEnrollment(Enrollment enrollment) {
        EntityManager em = Connector.getEmf().createEntityManager();
        em.getTransaction().begin();
        em.persist(enrollment);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Enrollment removeEnrollment(Integer enrollmentId) {
        EntityManager em = Connector.getEmf().createEntityManager();
        Enrollment enrollment = em.find(Enrollment.class, enrollmentId);

        if (enrollment != null) {
            em.getTransaction().begin();
            em.remove(enrollment);
            em.getTransaction().commit();
        }
        em.close();
        return enrollment;
    }

    @Override
    public Enrollment removeEnrollment(String socialSecurity, Integer courseId) {
        EntityManager em = Connector.getEmf().createEntityManager();
        Enrollment enrollment = em
                .createQuery("Select e from Enrollment e where e.course.courseId = :courseId AND e.student.socialSecurity = :socialSecurity", Enrollment.class)
                .setParameter("courseId", courseId)
                .setParameter("socialSecurity", socialSecurity)
                .getSingleResult();

        if (enrollment != null) {
            em.getTransaction().begin();
            em.remove(enrollment);
            em.getTransaction().commit();
        }
        em.close();
        return enrollment;
    }

    @Override
    public Enrollment gradeEnrollment(Integer enrollmentId, Integer grade) {
        EntityManager em = Connector.getEmf().createEntityManager();
        Enrollment enrollment = em.find(Enrollment.class, enrollmentId);

        if (enrollment != null) {
            em.getTransaction().begin();
            enrollment.setGrade(grade);
            em.getTransaction().commit();
        }
        em.close();
        return enrollment;
    }

    @Override
    public Enrollment setCourse(Integer enrollmentId, Integer courseId) {
        EntityManager em = Connector.getEmf().createEntityManager();
        Enrollment enrollment = em.find(Enrollment.class, enrollmentId);
        Course course = em.find(Course.class, courseId);

        if (enrollment != null) {
            em.getTransaction().begin();
            enrollment.setCourse(course);
            em.getTransaction().commit();
        }
        em.close();

        return enrollment;
    }

    @Override
    public Enrollment setStudent(Integer enrollmentId, String socialSecurity) {
        EntityManager em = Connector.getEmf().createEntityManager();
        Enrollment enrollment = em.find(Enrollment.class, enrollmentId);
        Student student = em.find(Student.class, socialSecurity);

        if (enrollment != null) {
            em.getTransaction().begin();
            enrollment.setStudent(student);
            em.getTransaction().commit();
        }
        em.close();

        return enrollment;
    }
}