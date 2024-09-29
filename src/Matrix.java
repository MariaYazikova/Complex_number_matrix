import java.util.Scanner;//импорт Scanner для обработки ввода

//класс матрицы комплексных чисел
class Matrix {
    private ComplexNumbers[][] arr; //двумерный массив для комплексных чисел
    private int rows; //кол-во строк матрицы
    private int columns; //кол-во столбцов матрицы

    //конструктор по умолчанию
    public Matrix(){
        this.rows = 0;
        this.columns = 0;
        this.arr = new ComplexNumbers[rows][columns];
    }
    //конструктор с параметрами
    public Matrix(int row, int col){
        this.rows = row;
        this.columns = col;
        this.arr = new ComplexNumbers[rows][columns];
    }

    //возвращение комплексного числа из матрицы
    public ComplexNumbers GetElement(int row, int col){
        return this.arr[row][col];
    }
    //добавление комплексного числа в матрицу
    public void SetElement(int row, int col, ComplexNumbers value){
        this.arr[row][col] = value;
    }

    //сложение двух матриц
    public Matrix addition(Matrix second){
        if (this.rows!= second.rows || this.columns!=second.columns){
            return null; //если размеры матриц отличаются, пользователь не может их сложить
        }
        else {
            Matrix res = new Matrix(rows, columns);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    ComplexNumbers value = this.GetElement(i, j).addition(second.GetElement(i, j));
                    res.SetElement(i, j, value);
                }
            }
            return res;
        }
    }

    //вычитание двух матриц
    public Matrix subtraction(Matrix second){
        if (this.rows!= second.rows || this.columns!=second.columns){
            return null; //если размеры матриц отличаются, пользователь не может выполнить вычитание
        }
        else {
            Matrix res = new Matrix(rows, columns);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    ComplexNumbers value = this.GetElement(i, j).subtraction(second.GetElement(i, j));
                    res.SetElement(i, j, value);
                }
            }
            return res;
        }
    }

    //умножение матрицы n1*m1 на матрицу n2*m2
    public Matrix multiplication(Matrix second){
        if(this.columns != second.rows){
            return null; //если m1!=n2, пользователь не может выполнить умножение
        }
        else{
            Matrix res = new Matrix(this.rows, second.columns); //результирующая матрица n1*m2
            for(int i=0; i<this.rows; i++){
                for(int j=0; j<second.columns; j++){
                    ComplexNumbers new_val = new ComplexNumbers(); //создание числа 0+0i
                    for(int r = 0; r <this.columns; r++){ //сложение произведений множителей в new_wal
                        ComplexNumbers value = this.GetElement(i, r).multiplication(second.GetElement(r,j));
                        new_val = new_val.addition(value);
                    }
                    res.SetElement(i, j, new_val);
                }
            }
            return res;
        }
    }

    //транспонирование матрицы
    public Matrix transposition(){
        Matrix res = new Matrix(columns, rows);
        for (int i=0; i<columns; i++){
            for (int j=0; j<rows; j++){
                ComplexNumbers value = this.GetElement(j, i);
                res.SetElement(i, j, value);
            }
        }
        return res;
    }

    //нахождение подматрицы из исходной матрицы с помощью исключения ind1 строки и ind2 столбца
    public Matrix minor(int ind1, int ind2){
        int new_row = rows-1;
        int new_col = columns-1;
        Matrix res = new Matrix(new_row, new_col);
        int minor_row = 0; //номер строки подматрицы
        for(int i=0; i<rows; i++){
            if(i==ind1){
                continue;
            }
            int minor_col = 0; //номер столбца подматрицы
            for(int j=0; j<columns; j++){
                if(j==ind2){
                    continue;
                }
                ComplexNumbers value = this.GetElement(i,j);
                res.SetElement(minor_row, minor_col, value);
                minor_col++;
            }
            minor_row++;
        }
        return res;
    }

    //нахождение определителя матрицы
    public ComplexNumbers determinant(){
        if (rows!=columns){
            return null; //если матрица не квадратная, пользователь не может найти определитель
        }
        else {
            if (rows == 1){ //базовый случай для матрицы 1*1
                return GetElement(0,0);
            }
            if (rows == 2){ //базовый случай для матрицы 2*2
                ComplexNumbers a = GetElement(0,0);
                ComplexNumbers b = GetElement(0,1);
                ComplexNumbers c = GetElement(1,0);
                ComplexNumbers d = GetElement(1,1);
                return a.multiplication(d).subtraction(b.multiplication(c));
            }
            ComplexNumbers res = new ComplexNumbers(); //создание числа 0+0i
            for(int i=0; i<columns; i++){
                //прохождение по числам из первого ряда матрицы и умножение их на детерминанты подматриц
                if(i%2!=0){ //если номер столбца нечетный, то применяем операцию -(a+bi) к a+bi
                    ComplexNumbers det_term = GetElement(0,i).multiplication(minor(0,i).determinant()).negative();
                    res = res.addition(det_term); //сложение найденных произведений
                }
                else{
                    ComplexNumbers det_term = GetElement(0,i).multiplication(minor(0,i).determinant());
                    res = res.addition(det_term);
                }
            }
            return res;
        }
    }


    //нахождение обратной матрицы
    public Matrix inverse(){
        if (rows!=columns){ //если матрица не квадратная, найти обратную к ней нельзя
            return null;
        }
        else {
            ComplexNumbers det = this.determinant(); //нахождение определителя
            if(det.GetRe()==0 && det.GetIm()==0){ //если определитель = 0, найти обратную нельзя
                return null;
            }
            else {
                Matrix inverse_mat = new Matrix(rows, columns);
                if(rows==1){ //если матрица состоит из одного числа a+bi, то обратная для нее 1/(a+bi)
                    ComplexNumbers value = new ComplexNumbers(1,0).division(det);
                    inverse_mat.SetElement(0,0, value);
                }
                else {
                    Matrix trans = this.transposition(); //транспонирование матрицы
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < columns; j++) {
                            //создание союзной матрицы - trans.minor(i, j).determinant()
                            //и деление её элементов на детерминант - .division(det);
                            if ((i + j) % 2 == 0) {
                                //операция -(а+bi) к a+bi, находящимся на "нечетных" позициях
                                ComplexNumbers value = trans.minor(i, j).determinant().division(det);
                                inverse_mat.SetElement(i, j, value);
                            } else {
                                ComplexNumbers value = trans.minor(i, j).determinant().division(det).negative();
                                inverse_mat.SetElement(i, j, value);
                            }
                        }
                    }
                }
                return inverse_mat;
            }
        }
    }

    //заполнение матрицы комплексными числами с целыми вещественной и мнимой частями
    public void initialization(Scanner input){
        for (int i = 0; i< this.rows; i++){
            for (int j = 0; j< this.columns; j++){
                System.out.print("["+i+"]["+j+"]: ");
                int real = input.nextInt();
                int imaginary = input.nextInt();
                this.SetElement(i,j, new ComplexNumbers(real, imaginary));
            }
        }
    }

    //строковое представление матрицы для вывода
    public String toString(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                str.append(GetElement(i,j)).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }
}
