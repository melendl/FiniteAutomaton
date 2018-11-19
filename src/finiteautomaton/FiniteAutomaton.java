
package finiteautomaton;

/*
 * Program: Finite Automaton
 * Author: melendl
 * Description: FA accepts input strings that start with 0, 
 *              end with a 0, and have only 1s in between.
 *              FA will output whether the string is
 *              accepted or rejected.
 *              The next input symbol is fetched via a for loop.
 *              Transition function implemented as a switch statement.
 *
 */
class Input {
// input class for input string

    private String input;
    public int currentIndex; //increments with each state transition

    public Input(String input) {
        this.input = input;
    }

    public int Length() {
        return input.length();
    }

    public char Read() {
        return input.charAt(currentIndex);
    }
}

interface State {
// interface contains collection of abstract methods

    public State next(Input word);
}

enum States implements State {
// Define the transitions of every state inside each enum

    Start {
        // input string must begin with '0'
        @Override
        public State next(Input input) {
            switch (input.Read()) {
                case '0':
                    return Transition;
                default:
                    return Rejected;
            }
        }
    },
    Transition {
        // input string must be all 1s between the start and accept transitions
        @Override
        public State next(Input input) {
            input.currentIndex++;
            switch (input.Read()) {
                case '0':
                    if (input.currentIndex == input.Length() - 1) {
                        return Accepted;
                    } else {
                        return Rejected;
                    }
                case '1':
                    return Transition;
                default:
                    return Rejected;
            }
        }
    },
    Accepted {
        // input string must end with a '0'
        @Override
        public State next(Input input) {
            switch (input.Read()) {
                case '0':
                    return Accepted;
                default:
                    return Rejected;
            }
        }
    },
    Rejected {
        @Override
        public State next(Input word) {
            return Rejected;
        }
    };

    public abstract State next(Input word);
}

public class FiniteAutomaton {

    static Input _input = new Input("0111110");
    static State _state;

    public static void main(String[] args) {

        for (_state = States.Start; _state != null || _state != States.Rejected; _state = _state.next(_input)) {
            if (_state == States.Accepted) {
                System.out.println("Accepted");
                break;
            } else if (_state == States.Rejected) {
                System.out.println("Rejected");
                break;
            }
        }

        if (_state != States.Accepted && _state != States.Rejected) {
            System.out.println("Error");
        }
    }
}
