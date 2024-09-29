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
