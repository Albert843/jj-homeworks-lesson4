package org.example.models;
import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "courses")
public class Course {
    //region Поля
    private static final String[] titles = {"Математика", "История", "Философия", "Иностранный язык", "Литература", "ИЗО"};
    private static final Random random = new Random();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private int duration;
    //endregion

    //region Конструкторы
    public Course() {
    }
    public Course(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }
    public Course(int id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }
    //endregion

    //region Геттеры и сеттеры
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    //endregion

    //region Методы
    public static Course create() {
        return new Course(titles[random.nextInt(titles.length)], random.nextInt(1, 7));
    }

    public void updateTitle() {
        title = titles[random.nextInt(titles.length)];
    }

    public void updateDuration() {
        duration = random.nextInt(1,7);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
    //endregion
}
