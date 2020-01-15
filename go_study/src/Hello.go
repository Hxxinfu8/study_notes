package main

import "fmt"
var a = "1111"
var b string = "222"
func main() {
	fmt.Println("Ha~Ha~!")
	println("Hello World")
	println(&a, &b)
	slice := make([]int8, 10)
	slice = append(slice, 1)
	fmt.Println(slice)
}
