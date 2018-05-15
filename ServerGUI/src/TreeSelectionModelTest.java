
// Использование модели выделения дерева TreeSelectionModel
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import java.awt.*;

public class TreeSelectionModelTest extends JFrame
{
    private static final long serialVersionUID = 1L;

    // Текстовое поле для представления пути
    private JTextArea  taSelection = new JTextArea(7, 20);
    final   String     ROOT  = "Корневая запись";
    // Массив листьев деревьев
    final   String[]   nodes = new String[]  {"Напитки", "Сладости"};
    final   String[][] leafs = new String[][]{{"Чай", "Кофе", "Коктейль", "Сок", "Морс", "Минералка"},
            {"Пирожное", "Мороженое", "Зефир", "Халва"}};

    public TreeSelectionModelTest()
    {
        super("TreeSelectionModes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        getContentPane().add(contents);
        // Размещение текстового поля в нижней части интерфейса
        getContentPane().add(new JScrollPane(taSelection), BorderLayout.SOUTH);
        setSize(500, 300);
        // Вывод окна на экран
        setVisible(true);
    }
    // Иерархическая модель данных TreeModel для деревьев
    private TreeModel createTreeModel()
    {
        // Корневой узел дерева
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(ROOT);
        // Добавление ветвей - потомков 1-го уровня
        DefaultMutableTreeNode drink = new DefaultMutableTreeNode(nodes[0]);
        DefaultMutableTreeNode sweet = new DefaultMutableTreeNode(nodes[1]);
        // Добавление ветвей к корневой записи
        root.add(drink);
        root.add(sweet);
        // Добавление листьев - потомков 2-го уровня
        for ( int i = 0; i < leafs[0].length; i++)
            drink.add(new DefaultMutableTreeNode(leafs[0][i], false));
        for ( int i = 0; i < leafs[1].length; i++)
            sweet.add(new DefaultMutableTreeNode(leafs[1][i], false));
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


    public static void main(String[] args) {

        new TreeSelectionModelTest();
    }


}
