package org.example;

public class StudentResults{
    public static void main(String[] args){
        
        Student myStudent = new Student("Jane");
        Results myResults = new Results(39);

        System.out.println("Name: " + myStudent.getName() + ", Grade: " + myResults.getGrade());

    }
}

class Student{
    public String name;

    public Student(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}

class Results{
    public double mark;
    public char grade;

    public Results(double mark){
        this.mark = mark;
    }

    public char getGrade(){
        if(mark >= 80){
            grade = 'A';
        }
        else if(mark >= 60){
            grade = 'B';
        }
        else if(mark >= 40){
            grade = 'C';
        }
        else{
            grade = 'D';
        }

        return grade;
    }
}


