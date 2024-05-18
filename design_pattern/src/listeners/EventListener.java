package listeners;

import java.io.File;

public interface EventListener {
	void update(String eventType, File file);
}

/*package refactoring_guru.observer.example.listeners;

import java.io.File;

public interface EventListener {
    void update(String eventType, File file);
}
*/