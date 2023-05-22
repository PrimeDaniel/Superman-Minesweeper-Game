package logic;

import java.io.Serializable;

public class Question implements Serializable {
    private final String id;
    private final String subject_name;
    private final String coursename;
    private final String question_text;
    private final String question_number;
    private final String lecturer;

    public Question (String id, String subject_name,String coursename,String question_text,String question_number,String lecturer) {
        this.id = new String(id);
        this.subject_name = new String(subject_name);
        this.coursename = new String(coursename);
        this.question_text = new String(question_text);
        this.question_number = new String(question_number);
        this.lecturer = new String(lecturer);
    }





    public String getId() {
		return id;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public String getCoursename() {
		return coursename;
	}

	public String getQuestion_text() {
		return question_text;
	}

	public String getQuestion_number() {
		return question_number;
	}

	public String getLecturer() {
		return lecturer;
	}


}
