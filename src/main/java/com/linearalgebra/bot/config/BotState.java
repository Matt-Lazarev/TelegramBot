package com.linearalgebra.bot.config;

import com.linearalgebra.bot.entity.User;
import com.linearalgebra.bot.generator.MatrixFunctions;
import com.linearalgebra.bot.generator.TaskGenerator;
import com.linearalgebra.bot.sender.MessageSender;
import com.linearalgebra.bot.service.UserServiceImplementation;

public enum BotState {

    Blank{
        BotState next;

        @Override
        public void enter(BotContext context) { }

        @Override
        public void handleInput(BotContext context) {
            next = handleStandardInput(context);
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    Main{
        BotState next;

        @Override
        public void enter(BotContext context) {
            MessageSender.sendMessageWithKeyboard(context, "Привет! Я бот-калькулятор линейной алгебры!\n" +
                    "Нажми \"Обо мне\", если не знаком со мной." +
                    "\nУже работал со мной? Выбирай интересующий тебя раздел!");
        }

        @Override
        public void handleInput(BotContext context) {
            next = handleStandardInput(context);
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

   AboutMe{
        BotState next;

       @Override
       public void enter(BotContext context) {
           MessageSender.sendMessageWithKeyboard(context, "Я могу:\n" +
                   "1. Решать примеры из линейной алгебры;\n" +
                   "2. Улучшить твои навыки решения примеров;\n" +
                   "3. Показать теорию по решению примеров.");
       }

       @Override
       public void handleInput(BotContext context) {
           next = handleStandardInput(context);
       }

       @Override
       public BotState nextState() {
           return next;
       }
   },

    Calculator{
        BotState next;

        @Override
        public void enter(BotContext context) {
            MessageSender.sendMessageWithCalculatorKeyboard(context, "Выбери необходимый калькулятор:");
        }

        @Override
        public void handleInput(BotContext context) {
            next = handleStandardInput(context);
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    Train {
        BotState next;

        @Override
        public void enter(BotContext context) {
            MessageSender.sendMessageWithTrainKeyboard(context, "Выбери необходимый генератор примеров:");
        }

        @Override
        public void handleInput(BotContext context) {
            next = handleStandardInput(context);
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    Theory {
        BotState next;

        @Override
        public void enter(BotContext context) {
            MessageSender.sendMessageWithTheoryKeyboard(context, "Выбери интересующую тебя тему:");
        }

        @Override
        public void handleInput(BotContext context) {
            next = handleStandardInput(context);
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    Sum {
        BotState next;

        @Override
        public void enter(BotContext context) {
            double[][] resultMatrix = MatrixFunctions.sum(matrixA, matrixB);
            String strMatrix = MatrixFunctions.matrixToString(resultMatrix);
            MessageSender.sendMessage(context, "Результат:\n" + strMatrix);
            MessageSender.sendMessageWithCalculatorKeyboard(context, "Выбери необходимый калькулятор:");
            rows = 0;
            cols = 0;
            matrixB = null;
            matrixA = null;
        }

        @Override
        public void handleInput(BotContext context) {
            next = handleStandardInput(context);
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    Sub {
        BotState next;

        @Override
        public void enter(BotContext context) {
            double[][] resultMatrix = MatrixFunctions.sub(matrixA, matrixB);
            String strMatrix = MatrixFunctions.matrixToString(resultMatrix);
            MessageSender.sendMessage(context, "Результат:\n" + strMatrix);
            MessageSender.sendMessageWithCalculatorKeyboard(context, "Выбери необходимый калькулятор:");
            rows = 0;
            cols = 0;
            matrixB = null;
            matrixA = null;
        }

        @Override
        public void handleInput(BotContext context) {
            next = handleStandardInput(context);
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    Mul {
        BotState next;

        @Override
        public void enter(BotContext context) {
            double[][] resultMatrix = MatrixFunctions.mul(matrixA, matrixB);
            String strMatrix = MatrixFunctions.matrixToString(resultMatrix);
            MessageSender.sendMessage(context, "Результат:\n" + strMatrix);
            MessageSender.sendMessageWithCalculatorKeyboard(context, "Выбери необходимый калькулятор:");
            rows = 0;
            cols = 0;
            matrixB = null;
            matrixA = null;
        }

        @Override
        public void handleInput(BotContext context) {
            next = handleStandardInput(context);
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    Det {
        BotState next;

        @Override
        public void enter(BotContext context) {
            double result = MatrixFunctions.determinant(matrixA);
            MessageSender.sendMessage(context, "Результат:\n" + Double.toString(result));
            MessageSender.sendMessageWithCalculatorKeyboard(context, "Выбери необходимый калькулятор:");
            rows = 0;
            cols = 0;
            matrixA = null;
        }

        @Override
        public void handleInput(BotContext context) {
            next = handleStandardInput(context);
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    SizeCheck {
        BotState next;
        @Override
        public void enter(BotContext context) { }

        @Override
        public void handleInput(BotContext context) {
            next = handleBreakStandardInput(context);
            if(next!=null)
                return;

            String input = context.getInput();

            String[] strings = input.split(" ");
            if (strings.length != 2) {
                MessageSender.sendMessage(context, "Количество строк и столбцов должны быть указаны через пробел!");
                next = this;
                return;
            }
            if(operation=='d' && !strings[0].equals(strings[1])){
                MessageSender.sendMessage(context, "Определитель матрицы может быть вычислен только для квадратной матрицы!");
                next = this;
                return;
            }
            if(!strings[0].matches("[0-9]") || !strings[1].matches("[0-9]")){
                MessageSender.sendMessage(context, "Размеры должны быть целыми числами!");
                next = this;
                return;
            }
            rows = Integer.parseInt(strings[0]);
            cols = Integer.parseInt(strings[1]);
            next = MatrixCheck;
        }

        @Override
        public BotState nextState() {
            next.cols = cols;
            next.rows = rows;
            next.operation = operation;
            next.matrixA = null;
            next.matrixB = null;
            return next;
        }
    },

    MatrixCheck {
        BotState next;
        @Override
        public void enter(BotContext context) {
            if(matrixA==null)
                MessageSender.sendMessage(context, "Введи построчно первую матрицу через пробел:");
            else
                MessageSender.sendMessage(context, "Введи построчно вторую матрицу через пробел:");
        }

        @Override
        public void handleInput(BotContext context) {
            next = handleBreakStandardInput(context);
            if(next!=null)
                return;

            String input = context.getInput();

            String[] strings = input.replace("\n", " ").split(" ");
            if (strings.length != cols*rows) {
                MessageSender.sendMessage(context, "Неверное количество элементов матрицы!");
                next = this;
                return;
            }
            for(String s: strings){
                if(!s.matches("(-)?[0-9]+(\\.[0-9]+)?")){
                    MessageSender.sendMessage(context, "Элемент матрицы должен быть целым или дробным числом. Разделитель - точка!");
                    next = this;
                    return;
                }
            }

            if(matrixA==null){
                int k =0;
                matrixA = new double[rows][cols];
                for(int i=0; i<rows; i++){
                    for(int j=0; j<cols; j++){
                        matrixA[i][j] = Double.parseDouble(strings[k]);
                        k++;
                    }
                }
                if(operation!='d')
                    next = MatrixCheck;
                else
                    next = Det;
            }else{
                int k =0;
                int col = cols;
                int row = rows;
                if(operation=='*'){
                    col = rows;
                    row = cols;
                }
                matrixB = new double[row][col];
                for(int i=0; i<row; i++){
                    for(int j=0; j<col; j++){
                        matrixB[i][j] = Double.parseDouble(strings[k]);
                        k++;
                    }
                }
                switch (operation){
                    case '+': next = Sum; break;
                    case '-': next = Sub; break;
                    case '*': next = Mul; break;
                    case 'd': next = Det; break;
                }
            }

        }

        @Override
        public BotState nextState() {
            next.operation = operation;
            next.matrixA = matrixA;
            next.matrixB = matrixB;
            return next;
        }
    },

    AnswerCheck {
        BotState next;
        double[][] matrixA;
        double[][] matrixB;
        @Override
        public void enter(BotContext context) {
            TaskGenerator generator = new TaskGenerator();
            matrixA = generator.generate(operation);
            if(operation!='d' && operation!='*')
                matrixB = generator.generate(matrixA.length, matrixA[0].length);
            if(operation == '*')
                matrixB = generator.generate(matrixA[0].length, matrixA.length);


            String matrixAString = MatrixFunctions.matrixToString(matrixA);
            String matrixBString = null;
            if(matrixB!=null)
                matrixBString = MatrixFunctions.matrixToString(matrixB);

            switch(operation){
                case '+':
                    MessageSender.sendMessage(context, "Сложение матриц:\n" + matrixAString + "\n+\n" + matrixBString);
                    break;
                case '-':
                    MessageSender.sendMessage(context, "Вычитание матриц:\n" + matrixAString + "\n-\n" + matrixBString);
                    break;
                case '*':
                    MessageSender.sendMessage(context, "Умножение матриц:\n" + matrixAString + "\n*\n" + matrixBString);
                    break;
                case 'd':
                    MessageSender.sendMessage(context, "Определитель матрицы:\n" + matrixAString);
                    break;
            }
        }

        @Override
        public void handleInput(BotContext context) {
            next = handleBreakStandardInput(context);
            if(next!=null)
                return;

            String input = context.getInput();

            String[] strings = input.replace("\n", " ").split(" ");

            if (strings.length != matrixA.length*matrixA[0].length && operation!='d') {
                MessageSender.sendMessage(context, "Неверное количество элементов матрицы!");
                next = this;
                return;
            }
            for(String s: strings){
                if(!s.matches("(-)?[0-9]+(\\.[0-9]+)?")){
                    MessageSender.sendMessage(context, "Элемент матрицы должен быть целым или дробным числом. Разделитель - точка!");
                    next = this;
                    return;
                }
            }
            int k =0;
            double[][] resultMatrix = new double[matrixA.length][matrixA[0].length];
            for(int i=0; i<matrixA.length; i++){
                for(int j=0; j<matrixA[0].length; j++){
                    resultMatrix[i][j] = Double.parseDouble(strings[k]);
                    if(k+1 == strings.length)
                        break;
                    k++;
                }
            }
            boolean right = false;
            switch(operation){
                case '+':
                    right = MatrixFunctions.equals(MatrixFunctions.sum(matrixA, matrixB), resultMatrix);
                    break;
                case '-':
                    right = MatrixFunctions.equals(MatrixFunctions.sub(matrixA, matrixB), resultMatrix);
                    break;
                case '*':
                    right = MatrixFunctions.equals(MatrixFunctions.mul(matrixA, matrixB), resultMatrix);
                    break;
                case 'd':
                    right = MatrixFunctions.determinant(matrixA) == resultMatrix[0][0];
                    break;
            }

            if(right)
                MessageSender.sendMessage(context,"Правильно!");
            else{
                MessageSender.sendMessage(context, "Неправильный ответ, попробуй еще раз");
                next = this;
                return;
            }
            next = Train;
        }

        @Override
        public BotState nextState() {
            next.operation = operation;
            return next;
        }
    };

    private final boolean inputNeeded;
    int rows;
    int cols;
    double[][] matrixA;
    double[][] matrixB;
    char operation;

    public boolean isInputNeeded() {
        return inputNeeded;
    }

    public void setOperation(char op) {
        operation = op;
    }

    private static BotState[] states;
    BotState(){
        this.inputNeeded = true;

    }

    BotState(boolean inputNeeded) {
        this.inputNeeded = inputNeeded;
    }

    public static BotState getState(UserServiceImplementation userService, long chatId, String name){
        User user = userService.getUserByChatId(chatId);
        if(user == null){
            user = new User(chatId, getInitialState().ordinal(), name);
            userService.addUser(user);
            return getInitialState();
        }
        else{
            userService.addUser(user);
            return getById(user.getStateId());
        }
    }

    public static BotState getInitialState(){
        return getById(0);
    }

    public static BotState getById(int id){
        if(states == null)
            states = BotState.values();
        return states[id];
    }

    public abstract void enter(BotContext context);
    public abstract BotState nextState();
    public void handleInput(BotContext context) {}

    public BotState handleStandardInput(BotContext context){
        String input = context.getInput();

        switch(input){
            case "Обо мне": return AboutMe;
            case "Калькулятор": return Calculator;
            case "Тренировочные примеры": return Train;
            case "Теория": return Theory;
            case "/start":
            case "Начать":
                return Main;
            default: MessageSender.sendMessage(context, "Выбери пункт из списка кнопок!");
                return Blank;
        }
    }

    public BotState handleBreakStandardInput(BotContext context){
        String input = context.getInput();

        switch(input){
            case "Обо мне": return AboutMe;
            case "Калькулятор": return Calculator;
            case "Тренировочные примеры": return Train;
            case "Теория": return Theory;
            case "/start":
            case "Начать":
                return Main;
            default: return null;
        }
    }
}
