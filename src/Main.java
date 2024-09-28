import java.util.Scanner;//импорт Scanner для обработки ввода
import java.text.DecimalFormat; //импорт DecimalFormat для обработки вывода

//класс комплексных чисел
class ComplexNumbers {
    private double re; //вещественная часть
    private double im; //мнимая часть

    //конструктор по умолчанию
    public ComplexNumbers(){
        this.re = 0;
        this.im = 0;
    }
    //конструктор с параметрами int для обработки целых значений, которые ввёл пользователь
    public ComplexNumbers(int re_val, int im_val){
        this.re = re_val;
        this.im = im_val;
    }
    //конструктор с параметрами double для выполнения операций
    public ComplexNumbers(double re_val, double im_val){
        this.re = re_val;
        this.im = im_val;
    }

    //возвращение вещественной и мнимой частей числа
    public double GetRe(){
        return this.re;
    }
    public double GetIm(){
        return this.im;
    }

    //сложение двух комплексных чисел
    public ComplexNumbers addition(ComplexNumbers second){
        double real = this.re + second.re;
        double imaginary = this.im + second.im;
        return new ComplexNumbers(real, imaginary);
    }

    //вычитание двух комплексных чисел
    public ComplexNumbers subtraction(ComplexNumbers second){
        double real = this.re - second.re;
        double imaginary = this.im - second.im;
        return new ComplexNumbers(real, imaginary);
    }

    //умножение двух комплексных чисел
    public ComplexNumbers multiplication(ComplexNumbers second){
        double real = this.re * second.re - this.im * second.im;
        double imaginary = this.im * second.re + this.re * second.im;
        return new ComplexNumbers(real, imaginary);
    }

    //операция -(a+bi) для a+bi
    public ComplexNumbers negative(){
        double real = -this.re;
        double imaginary = -this.im;
        return new ComplexNumbers(real, imaginary);
    }

    //сопряженное для комплексного числа
    public ComplexNumbers conjugate(){
        double imaginary = -this.im;
        return new ComplexNumbers(this.re, imaginary);
    }

    //деление комплексных чисел
    public ComplexNumbers division(ComplexNumbers second){
        ComplexNumbers conjugate_val = second.conjugate(); //сопряженное для числа, на которое делим
        ComplexNumbers numerator = this.multiplication(conjugate_val); //числитель дроби
        ComplexNumbers denominator = second.multiplication(conjugate_val); //знаменатель дроби
        double real = numerator.re/denominator.re;
        double imaginary = numerator.im/denominator.re;
        return new ComplexNumbers(real, imaginary);
    }

    //строковое представление комплексного числа для вывода
    public String toString(){
        StringBuilder str = new StringBuilder();
        //создание шаблона, который округляет дробные числа до трех цифр после запятой
        DecimalFormat pattern = new DecimalFormat("#.###");
        String new_re = pattern.format(re);
        String new_im = pattern.format(im);
        if(!(new_re.equals("0")) && !(new_re.equals("-0"))){ //добавление вещественной части, если она не равна нулю
            str.append(new_re);
        }
        if(!(new_im.equals("0")) && !(new_im.equals("-0"))){ //добавление мнимой части, если она не равна нулю
            if(im > 0 && re!=0){
                //со знаком "+", если она положительна, и до нее была вещественная часть
                str.append("+").append(new_im).append("i");
            }
            else{ //со знаком "-" в противном случае
                str.append(new_im).append("i");
            }
        }
        if((new_im.equals("0") || new_im.equals("-0")) && (new_re.equals("0") || new_re.equals("-0"))){
            str.append("0"); //если вещественная и мнимая части равны нулю, то просто добавляем "0"
        }
        return str.toString();
    }
}

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

//основной класс
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //просьба внести пользователю размеры матриц
        System.out.print("Enter the number of rows and columns for the matrix №1: ");
        int rows1 = input.nextInt();
        int columns1 = input.nextInt();
        while(rows1<=0 || columns1<=0){
            //проверка на внесение только натуральных чисел
            System.out.print("You can only enter natural values. Try again: ");
            rows1 = input.nextInt();
            columns1 = input.nextInt();
        }

        System.out.print("Enter the number of rows and columns for the matrix №2: ");
        int rows2 = input.nextInt();
        int columns2 = input.nextInt();
        while(rows2<=0 || columns2<=0){
            System.out.print("You can only enter natural values. Try again: ");
            rows2 = input.nextInt();
            columns2 = input.nextInt();
        }

        //просьба внести пользователю вещественную и мнимую части комплексного числа
        //в конкретные позиции матрицы
        Matrix matrix1 = new Matrix(rows1, columns1);
        System.out.println("Enter the real and imaginary parts of " +
                "complex number for the given positions in the first matrix: ");
        matrix1.initialization(input);

        Matrix matrix2 = new Matrix(rows2,columns2);
        System.out.println("Enter the real and imaginary parts of " +
                "complex number for the given positions in the second matrix: ");
        matrix2.initialization(input);
        input.close();

        //вывод созданных матриц
        System.out.println("Your matrix №1:\n" + matrix1);

        System.out.println("Your matrix №2:\n" + matrix2);

        //вывод сложения матриц
        Matrix add_res = matrix1.addition(matrix2);
        if (add_res != null ){
            System.out.println("matrix №1 + matrix №2 =\n" + add_res);
        }
        else{
            System.out.println("You can't add these matrices :(");
        }

        //вывод вычитания матриц
        Matrix sub_res = matrix1.subtraction(matrix2);
        if(sub_res!=null){
            System.out.println("matrix №1 - matrix №2 =\n" + sub_res);
        }
        else{
            System.out.println("You can't subtract these matrices :(");
        }

        //вывод умножения матриц
        Matrix mul_res = matrix1.multiplication(matrix2);
        if(mul_res!=null){
            System.out.println("matrix №1 * matrix №2 =\n" + mul_res);
        }
        else{
            System.out.println("You can't multiply these matrices :(");
        }

        //вывод транспонированных матриц
        Matrix trans_m1 = matrix1.transposition();
        Matrix trans_m2 = matrix2.transposition();
        System.out.println("Transposed matrix №1 =\n" + trans_m1);
        System.out.println("Transposed matrix №2 =\n" + trans_m2);

        //вывод определителей матриц
        ComplexNumbers determinant_m1 = matrix1.determinant();
        if(determinant_m1!=null){
            System.out.println("Determinant of matrix №1 = " + determinant_m1);
        }
        else{
            System.out.println("You can't calculate the determinant for matrix №1 :(");
        }

        ComplexNumbers determinant_m2 = matrix2.determinant();
        if(determinant_m2!=null){
            System.out.println("Determinant of matrix №2 = " + determinant_m2 +"\n");
        }
        else{
            System.out.println("You can't calculate the determinant for matrix №2 :(");
        }

        //вывод обратных матриц
        Matrix inverse_mat1 = matrix1.inverse();
        if (inverse_mat1 !=null) {
            System.out.println("Inverse matrix №1:\n" + inverse_mat1);
        }
        else{
            System.out.println("You can't find the inverse of matrix №1 :(");
        }
        Matrix inverse_mat2 = matrix2.inverse();
        if (inverse_mat2!=null) {
            System.out.println("Inverse matrix №2:\n" + inverse_mat2);
        }
        else{
            System.out.println("You can't find the inverse of matrix №2 :(");
        }

        //вывод деления матриц
        if (inverse_mat2 != null) {
            Matrix diversion1 = matrix1.multiplication(inverse_mat2);
            if (diversion1 != null) {
                System.out.println("matrix №1 / matrix №2 =\n" + diversion1);
            } else {
                System.out.println("You can't perform (matrix №1/ matrix №2) :(");
            }
        }
        else{
            System.out.println("You can't perform (matrix №1/ matrix №2) :(");
        }

        if (inverse_mat1!=null) {
            Matrix diversion2 = matrix2.multiplication(inverse_mat1);
            if (diversion2 != null) {
                System.out.println("matrix №2 / matrix №1 =\n" + diversion2);
            } else {
                System.out.println("You can't perform (matrix №2/ matrix №1) :(");
            }
        }
        else{
            System.out.println("You can't perform (matrix №2/ matrix №1) :(");
        }
    }
}