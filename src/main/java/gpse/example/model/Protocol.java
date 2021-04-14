package gpse.example.model;

import java.util.ArrayList;
import java.util.List;

public class Protocol {
    private List<Envelope> history;
    private final int ID;
    private static final String PATH = "/dasIstDerPfadZumProtokoll";
    private User owner;
    private List<Signature> signatures;
    private List<Signature> oldSignatures;




    public Protocol(Envelope env) {
        signatures = new ArrayList<>();
        oldSignatures = new ArrayList<>();
        owner = env.getOwner();
        history.add(env);
        ID = generateID();
    }


    void addVersion(Envelope newEnv){
        history.add(newEnv);
        oldSignatures.addAll(signatures);
        signatures.clear();
    }

    private int generateID() {
        return (int) (Math.random()*100);
    }

    public User getOwner() {
        return owner;
    }

    Envelope getCurrentEnv() {
        return history.get(history.size() - 1);
    }

    void addSignature(Signature sig){
        if (getCurrentEnv().isCorrectSignature(sig)){
            signatures.add(sig);
        }
    }
}
