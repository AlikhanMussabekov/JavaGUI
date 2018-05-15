import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MultipleUserServer {
    public static final int PORT = 1488;
    private static final String USERNAME = ".user.name";
    private static final String PATH = "/programming/Java/lab5/.password";


    private static class ServerGUI extends JFrame implements ActionListener {

        JTextField passwordTextField;
        JLabel passwordCheckLabel;
        JFrame loginFrame;

        JTextArea taSelection = new JTextArea(7, 20);
        final String ROOT = "Корневая запись";

        // Массив листьев деревьев
        final String[] nodes = new String[]{"Жители"};

        ConcurrentSkipListSetCollection collection = new ConcurrentSkipListSetCollection();

        final String[][] leafs = new String[][]{{"Чай", "Кофе", "Коктейль", "Сок", "Морс", "Минералка"}};



        private ServerGUI() {
            super("Сервер");
            login();
        }

        public void login(){

            loginFrame = new JFrame("Вход");

            loginFrame.setSize(300,150);
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

            loginFrame.setVisible(true);
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

            }
        }

        private void init() {
            JFrame mainFrame = new JFrame("Сервер");


            mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

            // Создание модели дерева
            TreeModel model = createTreeModel();

            // Дерево с выделением прерывных интервалов
            JTree tree3 = new JTree(model);

            // Определение отдельной модели выделения
            TreeSelectionModel selModel = new DefaultTreeSelectionModel();
            selModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

            // Подключение моделей выделения
            tree3.setSelectionModel(selModel);

            // Подключаем слушателя выделения
            tree3.addTreeSelectionListener(new SelectionListener());

            // Панель деревьев
            JPanel contents = new JPanel(new GridLayout(1, 1));

            // Размещение деревьев в интерфейсе
            contents.add(new JScrollPane(tree3));
            mainFrame.getContentPane().add(contents);

            // Размещение текстового поля в нижней части интерфейса
            mainFrame.getContentPane().add(new JScrollPane(taSelection), BorderLayout.SOUTH);
            mainFrame.setSize(500, 300);

            // Вывод окна на экран
            mainFrame.setVisible(true);

        }

                // Иерархическая модель данных TreeModel для деревьев
            private TreeModel createTreeModel()
            {
                // Корневой узел дерева
                DefaultMutableTreeNode root = new DefaultMutableTreeNode(ROOT);

                // Добавление ветвей - потомков 1-го уровня
                DefaultMutableTreeNode citizens = new DefaultMutableTreeNode(nodes[0]);

                // Добавление ветвей к корневой записи
                root.add(citizens);

                // Добавление листьев - потомков 2-го уровня
                for ( int i = 0; i < leafs[0].length; i++)
                    citizens.add(new DefaultMutableTreeNode(leafs[0][i], false));

                // Создание стандартной модели
                return new DefaultTreeModel(root);
            }


            // Слушатель выделения узла в дереве
            class SelectionListener implements TreeSelectionListener
            {
                public void valueChanged(TreeSelectionEvent e)
                {
                    if (taSelection.getText().length() > 0)
                        taSelection.append("-----------------------------------\n");
                    // Источник события - дерево
                    JTree tree = (JTree)e.getSource();

                    // Объекты-пути ко всем выделенным узлам дерева
                    TreePath[] paths = e.getPaths();
                    taSelection.append(String.format("Изменений в выделении узлов : %d\n", paths.length));
                    // Список выделенных элементов в пути
                    TreePath[] selected = tree.getSelectionPaths();
                    int[] rows = tree.getSelectionRows();
                    // Выделенные узлы
                    for (int i = 0; i < selected.length; i++) {
                        taSelection.append(String.format("Выделен узел : %s  (строка %d)\n",
                                selected[i].getLastPathComponent(), rows[i]));
                    }
                    // Отображение полных путей в дереве для выделенных узлов
                    for (int j = 0; j < selected.length; j++) {
                        TreePath path = selected[j];
                        Object[] nodes = path.getPath();
                        String text = "ThreePath : ";
                        for (int i = 0; i < nodes.length; i++) {
                            // Путь к выделенному узлу
                            DefaultMutableTreeNode node = (DefaultMutableTreeNode)nodes[i];
                            if (i > 0)
                                text += " >> ";
                            text += String.format("(%d) ", i) + node.getUserObject();
                        }
                        text += "\n";
                        taSelection.append(text);
                    }
                }
            }
        }

    public static void main(String[] args) throws ClassNotFoundException {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerGUI();
            }
        });

        ServerSocket serverSocket = null;
        Socket socket = null;

        ConcurrentSkipListSetCollection curSet = new ConcurrentSkipListSetCollection();
        JsonCommandParser parser = new JsonCommandParser();
        Scanner cmdScanner;

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