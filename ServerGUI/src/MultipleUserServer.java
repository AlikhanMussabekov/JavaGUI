import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MultipleUserServer {
    private static final int PORT = 1488;
    private static final String USERNAME = ".user.name";
    private static final String PATH = "/programming/Java/lab5/.password";

    private static ConcurrentSkipListSetCollection curSet = new ConcurrentSkipListSetCollection();


    private static class ServerGUI extends JFrame implements ActionListener {

        JTextField passwordTextField;
        JLabel passwordCheckLabel;
        JFrame loginFrame;
        JFrame mainFrame;
        TreeModel treeModel;
        JTree tree3;

        private Citizens selectedCitizen;
        private int selectedCitizenIndex;

        final String ROOT = "Жители";

        ConcurrentSkipListSetCollection collection = new ConcurrentSkipListSetCollection();


        private ServerGUI() {
            super("Сервер");
            login();
        }

        private void login(){

            loginFrame = new JFrame("Вход");

            loginFrame.setLocationByPlatform(true);

            loginFrame.setSize(300,150);
            loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            loginFrame.setMinimumSize(new Dimension(300,150));

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            passwordTextField = new JPasswordField(10);
            passwordTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            passwordTextField.setMaximumSize(new Dimension(150,20));

            passwordTextField.setActionCommand("textFieldAction");

            JButton enterButton = new JButton("Войти");

            passwordTextField.addActionListener(this);
            enterButton.addActionListener(this);

            JLabel greetingLabel = new JLabel("Добро пожаловать!");
            greetingLabel.setFont(new Font("Courier New", Font.ITALIC, 25));

            passwordCheckLabel = new JLabel(" ");

            greetingLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            passwordTextField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            passwordCheckLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            enterButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);

            panel.add(greetingLabel);
            panel.add(passwordTextField);
            panel.add(passwordCheckLabel);
            panel.add(enterButton);

            loginFrame.add(panel);

            loginFrame.pack();
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
        }



        private void init() {
            mainFrame = new JFrame("Сервер");

            mainFrame.setLocationByPlatform(true);

            mainFrame.setMinimumSize(new Dimension(1200,400));
            mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

            JMenuBar menuBar = new JMenuBar();

            JMenu menu = new JMenu("File");
            menu.setForeground(Color.BLACK);
            JMenuItem read = new JMenuItem("Считать коллекцию");
            JMenuItem save = new JMenuItem("Сохранить коллекцию");
            menu.add(read);
            menu.add(save);
            menu.addSeparator();
            JMenuItem logout = new JMenuItem("Выйти");

            menu.add(logout);

            read.addActionListener(this);
            save.addActionListener(this);
            logout.addActionListener(this);

            menuBar.add(menu);
            menuBar.setForeground(Color.GRAY);

            treeModel = createTreeModel();

            // Дерево с выделением прерывных интервалов
            tree3 = new JTree(treeModel);

            // Подключение моделей выделения
            tree3.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

            // Подключаем слушателя выделения
            tree3.addTreeSelectionListener(new SelectionListener());

            // Панель деревьев
            JPanel contents = new JPanel(new GridLayout(1,2));

            JPanel buttonsFrame = new JPanel(new GridLayout(1,6));

            JButton addElement = new JButton("Добавить элемент");
            JButton editElement = new JButton("Редактировать элемент");
            JButton removeElement = new JButton("Удалить элемент");
            JButton addIfMax = new JButton("Добавить максимальный");
            JButton addIfMin = new JButton("Добавить минимальный");
            JButton removeGreater = new JButton("Удалить больше, чем");

            addElement.addActionListener(this);
            editElement.addActionListener(this);
            removeElement.addActionListener(this);
            addIfMax.addActionListener(this);
            addIfMin.addActionListener(this);
            removeGreater.addActionListener(this);


            buttonsFrame.add(addElement);
            buttonsFrame.add(editElement);
            buttonsFrame.add(removeElement);
            buttonsFrame.add(addIfMax);
            buttonsFrame.add(addIfMin);
            buttonsFrame.add(removeGreater);


            mainFrame.setJMenuBar(menuBar);
            // Размещение деревьев в интерфейсе
            contents.add(new JScrollPane(tree3));
            //getContentPane().add(contents);
            mainFrame.add(contents);
            // Размещение текстового поля в нижней части интерфейса
            //getContentPane().add(buttonsFrame, BorderLayout.SOUTH);
            mainFrame.add(buttonsFrame, BorderLayout.SOUTH);
            // Вывод окна на экран

            mainFrame.pack();
            mainFrame.setLocationRelativeTo(loginFrame);
            mainFrame.setVisible(true);

            mainFrame.addWindowListener(new WindowAdapter() {


                @Override
                public void windowDeactivated(WindowEvent wEvt) {
                    //((JFrame)wEvt.getSource()).setBackground(Color.DARK_GRAY);
                    /*

                    ЗАТЕМНЕНИЕ ОСНОВНОГО ОКНА

                     */
                }

                @Override
                public void windowActivated(WindowEvent wEvt) {
                    //((JFrame)wEvt.getSource()).setBackground(Color.WHITE);
                    /*

                    ЗАТЕМНЕНИЕ ОСНОВНОГО ОКНА

                     */
                }
            });

        }

        public void add(){
            JFrame addFrame;

            JLabel nameLabel = new JLabel("Название:");
            JLabel ageLabel = new JLabel("Возраст:");
            JLabel checkLabel = new JLabel(" ");

            JTextField nameTF = new JTextField(10);
            JTextField ageTF = new JTextField(10);
            addFrame = new JFrame("Элемент");
            addFrame.setSize(300, 150);
            addFrame.setResizable(false);
            addFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            addFrame.setLocationRelativeTo(mainFrame);

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

            JPanel namePanel = new JPanel(new FlowLayout());
            JPanel agePanel = new JPanel(new FlowLayout());

            namePanel.setSize(250, 40);
            agePanel.setSize(250, 40);


            JButton submitButton = new JButton("Добавить");

            namePanel.add(nameLabel);
            namePanel.add(nameTF);

            agePanel.add(ageLabel);
            agePanel.add(ageTF);

            namePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            ageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            checkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (nameTF.getText().equals("") && ageTF.getText().equals("")){
                        checkLabel.setText("Заполните поля");
                        checkLabel.setForeground(Color.RED);
                        nameTF.setBorder(BorderFactory.createLineBorder(Color.RED));
                        ageTF.setBorder(BorderFactory.createLineBorder(Color.RED));
                    }else if (nameTF.getText().equals("")){
                        checkLabel.setText("Заполните поле");
                        checkLabel.setForeground(Color.RED);
                        nameTF.setBorder(BorderFactory.createLineBorder(Color.RED));
                        ageTF.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    }else if (ageTF.getText().equals("")){
                        checkLabel.setText("Заполните поле");
                        checkLabel.setForeground(Color.RED);
                        nameTF.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        ageTF.setBorder(BorderFactory.createLineBorder(Color.RED));
                    }else {

                        try {
                            String name = nameTF.getText();
                            int age = Integer.valueOf(ageTF.getText());

                            Citizens addedCitizen = new Citizens(name,age);

                            curSet.add_element(addedCitizen);
                            new InformationPane("Элемент успешно добавлен", mainFrame, "OK");

                            updateTree("add", 0, addedCitizen );
                            addFrame.dispose();

                        }catch(NumberFormatException ex){
                            checkLabel.setText("Введите число");
                            checkLabel.setForeground(Color.RED);
                            nameTF.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            ageTF.setBorder(BorderFactory.createLineBorder(Color.RED));
                        }
                    }
                }
            });

            contentPanel.add(namePanel);
            contentPanel.add(agePanel);
            contentPanel.add(checkLabel);
            contentPanel.add(submitButton);

            addFrame.add(contentPanel);

            addFrame.setVisible(true);
            //addFrame.pack();
            addFrame.addWindowListener(new WindowAdapter() {

                @Override
                public void windowDeactivated(WindowEvent wEvt) {
                    ((JFrame) wEvt.getSource()).dispose();
                }

            });
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(ae.getActionCommand().equals("Войти")){

                try {

                    String enteredPassword = passwordTextField.getText();


                    Scanner hashIn = new Scanner(new File(PATH));
                    int hcodePassFile = hashIn.nextInt();


                    if(enteredPassword.concat(USERNAME).hashCode() == hcodePassFile){
                        passwordCheckLabel.setText("Верный пароль");
                        passwordCheckLabel.setForeground(Color.GREEN);
                        passwordTextField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                        loginFrame.dispose();
                        init();
                    }else{
                        passwordTextField.setText("");
                        passwordCheckLabel.setText("Неверный пароль");
                        passwordCheckLabel.setForeground(Color.RED);
                        passwordTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }else if (ae.getActionCommand().equals("Считать коллекцию")){

                try {
                    curSet.readElements();
                    new InformationPane("Коллекция успешно считана",loginFrame, "OK");
                    mainFrame.repaint();
                    updateTree(" ",0,null);
                }catch (Exception e){
                    new InformationPane("Произошла ошибка", loginFrame, "ERROR");
                    e.printStackTrace();
                }

            }else if (ae.getActionCommand().equals("Сохранить коллекцию")){

                try {
                    curSet.save();
                    new InformationPane("Коллекция успешно сохранена", loginFrame, "OK");

                } catch (IOException e) {
                    new InformationPane("Произошла ошибка", loginFrame, "ERROR");
                }

            }else if (ae.getActionCommand().equals("Выйти")){

                mainFrame.dispose();
                login();

            }else if (ae.getActionCommand().equals("Удалить элемент")){

                curSet.removeElement(selectedCitizen);
                new InformationPane("Элемент успешно удален", loginFrame, "OK");

                updateTree("remove", selectedCitizenIndex, null);

            }else if (ae.getActionCommand().equals("Добавить элемент")) {
                System.out.println("111d1");

                add();

            }else if (ae.getActionCommand().equals("Редактировать элемент")){

            }
        }

        private void updateTree(String type, int index, Citizens curCitizen){

            DefaultTreeModel model = (DefaultTreeModel)tree3.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();

            switch(type){
                case "add":
                    root.add(new DefaultMutableTreeNode(curCitizen));
                    break;
                case "remove":
                    root.remove(index);
                    break;
                default:
                    model.reload(root);
                    break;
            }

            model.reload(root);
            mainFrame.repaint();
        }

        // Иерархическая модель данных TreeModel для деревьев
        private TreeModel createTreeModel()
        {
            // Корневой узел дерева
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(ROOT);

            // Добавление листьев
            for ( Citizens curCitizen: curSet.returnObjects()){
                DefaultMutableTreeNode citizenNode = new DefaultMutableTreeNode(curCitizen);

                root.add(citizenNode);
            }

            // Создание стандартной модели
            return new DefaultTreeModel(root);
        }

        // Слушатель выделения узла в дереве
        class SelectionListener implements TreeSelectionListener
        {

            public void valueChanged(TreeSelectionEvent se)
            {
                JTree tree = (JTree) se.getSource();
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree
                        .getLastSelectedPathComponent();

                Citizens selectedNodeName = (Citizens) selectedNode.getUserObject();

                if (selectedNode.isLeaf()) {
                    selectedCitizen = selectedNodeName;
                    selectedCitizenIndex = selectedNode.getParent().getIndex(selectedNode);
                    System.out.println(selectedCitizenIndex);
                }
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {

        SwingUtilities.invokeLater(ServerGUI::new);

        ServerSocket serverSocket = null;
        Socket socket = null;


        String pathStr = null;
        String check = null;

        Scanner path = new Scanner(System.getenv("PATH"));

        path.useDelimiter(":");

        while(path.hasNext()){

            pathStr = path.next();

            if (pathStr.substring(pathStr.length()-4,pathStr.length()).equals("lab5"))
                check = pathStr;

        }
        path.close();

/*
        Scanner path = new Scanner(System.getenv("LAB"));
        check = path.next();
*/

        try {
            curSet.setPath(check+"/str.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        curSet.readElements();
        curSet.writeElements();

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            try {
                System.out.println("Waiting for connection...");

                socket = serverSocket.accept();

                System.out.println("Connected: " + socket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }

            new Thread(new UserThread(curSet,socket)).start();
        }

    }
}