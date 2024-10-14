build:
	@kotlinc *.kt
run:
	@kotlinc *.kt
	@kotlin MainKt
	@make clean
clean:
	@rm *.class
