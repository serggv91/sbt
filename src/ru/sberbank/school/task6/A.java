package ru.sberbank.school.task6;

import ru.sberbank.school.task6.x.CImlp;

import java.util.List;

/**
 * Created by svetlana on 25.09.16.
 */
@Component
public class A { //TODO: не абстрактный и не интерфейс

    @Autowired
    private B b;

    @Autowired
    private CImlp d;


    @PostConstruct
    private void init() {
        //TODO: some logic
        System.out.println("init called");
    }

    public void execute() {
        System.out.println(b.getSomeData());
        System.out.println(d.getSomeStr());
    }

    @PreDestroy
    private void destroy() {
        //TODO: some logic
        System.out.println("destroy called");
    }

}
