import java.util.Scanner;//импорт Scanner для обработки ввода

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