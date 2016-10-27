package tru.sberbank.school.task12;

/**
 * Created by Sergei on 22.10.2016.
 */
public interface ThreadPool {

    void start(); // запускает потоки. Потоки бездействуют, до тех пор пока не появится новое задание в очереди (см. execute)

    void execute(Runnable runnable); // складывает это задание в очередь. Освободившийся поток должен выполнить это задание.
    // Каждое задание должны быть выполнено ровно 1раз

}