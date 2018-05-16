
// Использование модели выделения дерева TreeSelectionModel
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import java.awt.*;

public class TreeSelectionModelTest extends JFrame
{

    // Текстовое поле для представления пути
    //private JTextArea  taSelection = new JTextArea(7, 20);

    final   String     ROOT  = "Корневая запись";

    // Массив листьев деревьев
    final   String[]   nodes = new String[]  {"Жители"};

    ConcurrentSkipListSetCollection collection = new ConcurrentSkipListSetCollection();

    final   String[][] leafs = new String[][]{{"Чай", "Кофе", "Коктейль", "Сок", "Морс", "Минералка", "Кофе", "Коктейль", "Сок", "Морс"
            , "Кофе", "Коктейль", "Сок", "Морс", "Кофе", "Коктейль", "Сок", "Морс"}};



    public TreeSelectionModelTest()
    {


        super("TreeSelectionModes");
        setMinimumSize(new Dimension(800,400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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

        menuBar.add(menu);
        menuBar.setForeground(Color.GRAY);

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
        JPanel contents = new JPanel(new GridLayout(1,2));
        contents.setSize(new Dimension(800,300));

        JPanel buttonsFrame = new JPanel(new GridLayout(1,6));
        buttonsFrame.setSize(new Dimension(800, 100));

        JButton editElement = new JButton("editElement");
        JButton removeElement = new JButton("removeElement");
        JButton addIfMax = new JButton("addIfMax");
        JButton addIfMin = new JButton("addIfMin");
        JButton removeGreater = new JButton("removeGreater");
        JButton addElement = new JButton("addElement");

        buttonsFrame.add(addElement);
        buttonsFrame.add(editElement);
        buttonsFrame.add(removeElement);
        buttonsFrame.add(addIfMax);
        buttonsFrame.add(addIfMin);
        buttonsFrame.add(removeGreater);


        setJMenuBar(menuBar);
        // Размещение деревьев в интерфейсе
        contents.add(new JScrollPane(tree3));
        //getContentPane().add(contents);
        add(contents);
        // Размещение текстового поля в нижней части интерфейса
        //getContentPane().add(buttonsFrame, BorderLayout.SOUTH);
        add(buttonsFrame, BorderLayout.SOUTH);
        // Вывод окна на экран
        setVisible(true);
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
            /*if (taSelection.getText().length() > 0)
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
            }*/
        }
    }


    public static void main(String[] args) {

        new TreeSelectionModelTest();
    }


}
