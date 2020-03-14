ALTER TABLE final_labor.commission DROP FOREIGN KEY FK_commission_professor
ALTER TABLE final_labor.commission DROP FOREIGN KEY FK_commission_topic_of_student_labor
ALTER TABLE final_labor.topic DROP FOREIGN KEY FK_topic_professor
ALTER TABLE final_labor.topic_of_student_labor DROP FOREIGN KEY FK_topic_of_student_labor_topic
ALTER TABLE final_labor.topic_of_student_labor DROP FOREIGN KEY FK_topic_of_student_labor_student
DROP TABLE final_labor.commission
DROP TABLE final_labor.professor
DROP TABLE final_labor.student
DROP TABLE final_labor.topic
DROP TABLE final_labor.topic_of_student_labor
