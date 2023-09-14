package exercises;

public class Person {

    private String name;
    private int age;

    void turnYear() {
        age++;
        onYearTurned(EventArgs.empty);
    }


    private Event<Person, EventArgs> yearTurned = new Event<>();

    public Event<Person, EventArgs> getYearTurned() {
        return this.yearTurned;
    }

    protected void onYearTurned(EventArgs e) {
        if (yearTurned != null) {
            yearTurned.raiseEvent(this, e);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        //new NameChangedEventArgs() { { this.oldName = oldName, this.newName = name}};
        onNameChanged(new NameChangedEventArgs(oldName, this.name));
    }

    private final Event<Person, NameChangedEventArgs> nameChanged = new Event<Person,NameChangedEventArgs>();

    public Event<Person, NameChangedEventArgs> getNameChanged() {
        return nameChanged;
    }

    protected void onNameChanged(NameChangedEventArgs e) {
        nameChanged.raiseEvent(this, e);
    }

    public int getAge() {
        return age;
    }

    public static class NameChangedEventArgs extends EventArgs {
        protected String oldName;
        protected String newName;

        public NameChangedEventArgs(String oldName, String newName) {
            this.oldName = oldName;
            this.newName = newName;
        }

        public String getOldName() {
            return oldName;
        }

        public String getNewName() {
            return newName;
        }
    }

}
