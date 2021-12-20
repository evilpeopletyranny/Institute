import scala.annotation.tailrec

object Main extends App
{
  // 1. Рефакторинг кода для случая композиции  3 функций
  def threeFuncCompose0[A, B, C, D](f1: A => B)(f2: B => C)(f3: C => D): A => D = a => f3(f2(f1(a)))

  def join: List[Int] => List[Int] => List[(Int, Int)] = l1 => l2 => l1.zip(l2) // список => список => список пар

  def multiply(l: List[(Int, Int)]): List[Int] = {
    @tailrec
    def run(l: List[(Int, Int)], acc: List[Int]): List[Int] = {
      l match {
        case Nil => acc
        case h :: t => run(t, h._1 * h._2 :: acc)

      }
    }
    run(l, Nil)
  }

  def scalarProduct1: List[Int] => List[Int] => Int = a => threeFuncCompose0(join(a))(multiply)(sum)

  val v1 = List(1, 2, 3)
  val v2 = List(1, 2, 3)

  println(scalarProduct1(v1)(v2))


  def sum(l: List[Int]): Int = l.sum

  //2. НОД, НОК
  def NOD: Int => Int => Int = a => b =>{
    if (b == 0) a else NOD(b)(a % b)
  }

  def NOK: Int => Int => Int = a => b => a / NOD(a)(b) * b

  println("Наибольший общий делитель (30, 18): " + NOD(30)(18))
  println("Наименьшое общее кратное  (30, 18): " + NOK(30)(18))

  // 3. Определите функцию, принимающую на вход целое n и возвращающую список, содержащий n элементов, упорядоченных по возрастанию.
  //    - список натуральных чисел,
  //    - список нечётных натуральных чисел,
  //    - список чётных натуральных чисел,
  //    - список квадратов натуральных чисел,
  //    - список факториалов,
  //    - список степеней 2^i.

  def natural(n:Int): List[Int] = List.range(1, n)
  def odd(n:Int): List[Int] = List.range(1, n).filter(x => x % 2 == 1)
  def even(n:Int): List[Int] = List.range(1, n).filter(x => x % 2 == 0)
  def square(n:Int): List[Int] = List.range(1, n).map(x => x*x)
  def factorial(n:Int): List[Int] = List.range(1, n).map(x => (1 to x).product)
  def twoPower(n:Int): List[Int] = List.range(1, n).map(x => math.pow(2, x).toInt)

  println("Натуральные: " + natural(11))
  println("Нечетные   : " + odd(11))
  println("Четные     : " + even(11))
  println("Квадраты   : " + square(11))
  println("Факториалы : " + factorial(6))
  println("Степени 2  : " + twoPower(11))


  // 4. Определите следующие функции:
  //   4.1 Функция removeOdd, которая удаляет из заданного списка целых чисел все нечётные числа.
  //       Например removeOdd [1,4,5,6,10,6] должен возвращать [4,10], убрать дубликаты + вернуть 2 списка,
  //       отсортированных в прямом и обратном  порядке.
  //   4.2 Функция substitute :: Char -> Char -> String -> String, которая заменяет в строке указанный символ на заданный.
  //       Пример: substitute ‘e’ ‘i’ “eigenvalue” возвращает “iiginvalui”.

  def removeOdd(list:List[Int]):Iterable[List[Int]] = {
    val noDuplicate = list.distinct.filter(x => x%2 == 0)
    List(noDuplicate, noDuplicate.reverse)
  }
  val list = List(1, 4, 4, 5, 6, 10, 6)
  println("4.1 Исходный список: " + list)
  println("Получили: " + removeOdd(list))


  def substitute: Char => Char => String => String = C => H => str => for (ch <- str) yield ch match {
    case C => H
    case ch:Char => ch
  }
  println("4.2 Входная строка: eigenvalue")
  println(substitute('e')('i')("eigenvalue"))
}
