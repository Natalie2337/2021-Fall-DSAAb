import static java.lang.Double.doubleToLongBits;

public final class Try {//让这个类成为final，这样就不能够外界更改我的hash函数了
    //注意hashCode()的返回值只能是int！经过hash处理以后不管原来是什么值，都是整型了！
    private final double value;

    public Try(double value) {
        this.value = value;

    }

    public int hashCode(){
        long bits = doubleToLongBits(value);//用doubleToLongBits需要import lang下面的类
        return (int)(bits^(bits>>>32));//xor按位异或 most significant 32-bits with least significant 32-bits
    }


}
