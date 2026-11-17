package com.assignment.alumni.repository;
import com.assignment.alumni.entity.AlumniProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface AlumniRepository extends JpaRepository<AlumniProfile,Long> {
 Optional<AlumniProfile> findByLinkedinUrl(String linkedinUrl);
}