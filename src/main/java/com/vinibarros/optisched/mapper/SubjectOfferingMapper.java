package com.vinibarros.optisched.mapper;

import com.vinibarros.optisched.dto.request.SubjectOfferingRequest;
import com.vinibarros.optisched.dto.response.SubjectOfferingResponse;
import com.vinibarros.optisched.entity.Course;
import com.vinibarros.optisched.entity.Semester;
import com.vinibarros.optisched.entity.Subject;
import com.vinibarros.optisched.entity.SubjectOffering;
import org.springframework.stereotype.Component;

@Component
public class SubjectOfferingMapper {

    public SubjectOffering toEntity(SubjectOfferingRequest request, Course course, Subject subject, Semester semester){
        SubjectOffering subjectOffering = new SubjectOffering();
        subjectOffering.setCourse(course);
        subjectOffering.setSubject(subject);
        subjectOffering.setSemester(semester);
        subjectOffering.setSection(request.section());
        subjectOffering.setExpectedStudents(request.expectedStudents());
        subjectOffering.setRecommendedSemester(request.recommendedSemester());
        return subjectOffering;
    }

    public SubjectOfferingResponse toResponse(SubjectOffering subjectOffering){
        return new SubjectOfferingResponse(
                subjectOffering.getId(),
                subjectOffering.getCourse().getId(),
                subjectOffering.getSubject().getId(),
                subjectOffering.getSemester().getId(),
                subjectOffering.getSection(),
                subjectOffering.getExpectedStudents(),
                subjectOffering.getRecommendedSemester()
        );
    }
}
