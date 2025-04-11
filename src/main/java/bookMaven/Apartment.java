package bookMaven;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Apartment {

  private static final double ELECTRICITY_FACTOR = 3; // Константа для расчетов кабеля
  private static final double WATER_FACTOR = 1.1; // Константа для водопровода


  // @SuppressWarnings("unused")
  public static void main (String[] args) throws IOException {
	Scanner sc = new Scanner (System.in);
	System.out.println ("Введите название вашей квартиры (например: Квартира №.....):");
	String apartmentName = sc.nextLine ();

	try (PrintWriter writer = new PrintWriter (new FileWriter ("result.txt", false))) {
	// Инициализация переменных для подсчета итогов
	  double totalApartmentArea = 0;
	  double totalWallArea = 0;
	  double totalFloorArea = 0;
	  double totalApartmentPerimeter = 0;
	  double totalWindowsSlopeLength = 0;
	  double totalDoorsSlopeLength = 0;
	  double totalWindowsArea = 0;
	  double totalDoorsArea = 0;
	  int totalNumberOfSockets = 0;
	  double totalCableLength = 0;
	  int totalSwitches = 0;
	  double totalCableSwitches = 0;
	  int totalLamps = 0;
	  double totalCableLamps = 0;
	  int totalWaterSockets = 0;
	  double totalWaterPipeLength = 0;


	  writer.println ();
	  writer.println ("===== Новый запуск программы =====");
	  writer.println ();
	  writer.println ("Дата и время: " + LocalDateTime.now ().format (DateTimeFormatter.ofPattern ("yyyy-MM-dd HH:mm:ss")));

	  System.out.println ("ВВЕДИТЕ КОЛИЧЕСТВО КОМНАТ В КВАТИРЕ: ");
	  int roomCount = safeInputInt (sc);
	  sc.nextLine ();


	  //-----БЛОК 2  -------------------------------------------------------------------------------------------------
	  for (int r = 1; r <= roomCount; r++) {
		System.out.println ("КОМНАТА СТАНДАРТНАЯ (прямоугольная) или нет введите (ДА) или (НЕТ):");
		String isStandard = sc.nextLine ();

		System.out.println ("Введите название комнаты (например: Спальня, Кухня, Ванна, Туалет):");
		String roomName = sc.nextLine ();


		double wallArea, floorArea, roomPerimeter;
		double length, width, height;

		if (isStandard.equalsIgnoreCase ("да")) {
		  // Прямоугольная комната
		  //здесь данные берем со сканера
		  System.out.println ("Введите длину комнаты (м):");
		  length = safeInputDouble (sc);
		  sc.nextLine ();
		  System.out.println ("Введите ширину комнаты (м):");
		  width = safeInputDouble (sc);
		  sc.nextLine ();
		  System.out.println ("Введите высоту комнаты (м):");
		  height = safeInputDouble (sc);
		  sc.nextLine ();
		  // а здесь вызываем методы и данные оттуда берутся
		  floorArea = calcFloorArea (length, width);
		  wallArea = calcWallArea (length, width, height);
		  roomPerimeter = calcRoomPerimeter (length, width);

		} else {
		  // Нестандартная комната
		  //здесь данные берем со сканера
		  System.out.println ("Введите высоту стен (м):");
		  height = safeInputDouble (sc);
		  sc.nextLine ();
		  System.out.println ("Введите суммарный периметр нестандартного помещения:");
		  roomPerimeter = safeInputDouble (sc);
		  sc.nextLine ();
		  //а здесь мы сразу расчет сделали
		  wallArea = roomPerimeter * height;
		  // а здесь вызываем методы и данные оттуда берутся
		  floorArea = calcNonStandardFloorArea (sc);
		}

		//----------------------------------------------------------------------------------------------------------
		// ОКНА
		double roomWindowsArea = 0;
		double roomWindowsSlopeLength = 0;
		double openingWidth;
		double openingHeight;

		System.out.println ("Введите количество окон:");
		int windowsCount = safeInputInt (sc);
		sc.nextLine ();

		for (int w = 1; w <= windowsCount; w++) {
		  System.out.println ("Введите ширину окна " + w + " (в метрах):");
		  openingWidth = safeInputDouble (sc);
		  sc.nextLine ();
		  System.out.println ("Введите высоту окна " + w + " (в метрах):");
		  openingHeight = safeInputDouble (sc);
		  sc.nextLine ();

		  roomWindowsArea += calcOpeningArea (openingWidth, openingHeight);
		  roomWindowsSlopeLength += calcOpeningPerimeter (openingWidth, openingHeight);
		}
		//----------------------------------------------------------------------------
		// БЛОК 6 Обработка дверей
		double roomDoorsArea = 0;
		double roomDoorsSlopeLength = 0;

		System.out.println ("Введите количество дверей:");
		int doorsCount = safeInputInt (sc);
		sc.nextLine ();

		for (int d = 1; d <= doorsCount; d++) {
		  System.out.println ("Введите ширину двери " + d + " (в метрах):");
		  openingWidth = safeInputDouble (sc);
		  sc.nextLine ();
		  System.out.println ("Введите высоту двери " + d + " (в метрах):");
		  openingHeight = safeInputDouble (sc);
		  sc.nextLine ();

		  roomDoorsArea += calcOpeningArea (openingWidth, openingHeight);
		  roomDoorsSlopeLength += calcOpeningPerimeter (openingWidth, openingHeight);
		}

		double finalWallArea = wallArea - roomWindowsArea - roomDoorsArea;
		//ЭЛЕКТРИКА
		int roomSwitches;
		double roomCableSwitches;
		int roomLamps;
		double roomCableLamps;
		int roomNumberSockets;
		double distanceBetweenRooms;
		double cableLengthInsideRoom;

		System.out.println ("введите кол-во выключателей (шт.): ");
		roomSwitches = safeInputInt (sc);
		sc.nextLine ();
		System.out.println ("введите кол-во светильников (шт.): ");
		roomLamps = safeInputInt (sc);
		sc.nextLine ();
		System.out.println ("Введите количество розеток (шт.):");
		roomNumberSockets = safeInputInt (sc);
		sc.nextLine ();
		System.out.println ("Введите расстояние от щитка до комнаты (м):");
		distanceBetweenRooms = safeInputDouble (sc);
		sc.nextLine ();
		cableLengthInsideRoom = calculateCableLengthInsideRoom (roomPerimeter, roomNumberSockets, distanceBetweenRooms);
		roomCableSwitches = roomSwitches * roomPerimeter;
		roomCableLamps = roomLamps * roomPerimeter;

		//ВОДА
		int roomWaterSockets = 0;
		double roomWaterPipeLength = 0;

		if (roomName.equals ("Кухня") || roomName.equals ("Ванна") || roomName.equals ("Туалет")) {
		  System.out.println ("ведите кол-во водорозеток:");
		  roomWaterSockets = safeInputInt (sc);
		  sc.nextLine ();
		  roomWaterPipeLength = calcWaterPipeLength (roomPerimeter, roomWaterSockets);
		}
		// Итоги по комнате
		printRoomDetails (writer,roomName, floorArea, wallArea, roomPerimeter, roomWindowsArea, roomWindowsSlopeLength,
			roomDoorsArea, roomDoorsSlopeLength, roomSwitches, roomCableSwitches, roomLamps, roomCableLamps, roomNumberSockets, cableLengthInsideRoom, roomWaterPipeLength, roomWaterSockets);


		// Суммируем общие данные
		totalApartmentArea += floorArea;
		totalFloorArea += floorArea;
		totalWallArea += finalWallArea;
		totalApartmentPerimeter += roomPerimeter;
		totalWindowsArea += roomWindowsArea;
		totalWindowsSlopeLength += roomWindowsSlopeLength;
		totalDoorsArea += roomDoorsArea;
		totalDoorsSlopeLength += roomDoorsSlopeLength;
		totalSwitches += roomSwitches;
		totalCableSwitches += roomCableSwitches;
		totalLamps += roomLamps;
		totalCableLamps += roomCableLamps;
		totalNumberOfSockets += roomNumberSockets;
		totalCableLength += cableLengthInsideRoom;
		totalWaterSockets += roomWaterSockets;
		totalWaterPipeLength += roomWaterPipeLength;
	  }
	  //--------------------------------------------------------------------------------------------------
	  // Итоговый отчет
	  printApartmentDetails ( writer,apartmentName, totalApartmentArea, totalFloorArea,
		  totalWallArea, totalApartmentPerimeter, totalWindowsArea, totalWindowsSlopeLength,
		  totalDoorsArea, totalDoorsSlopeLength,  totalSwitches, totalCableSwitches, totalLamps, totalCableLamps, totalNumberOfSockets, totalCableLength,
		  totalWaterSockets, totalWaterPipeLength);


	} catch (IOException e) {
	  System.out.println ("ошибка при записи в файл" + e.getMessage ());
	}

	sc.close ();
  }


  // Безопасный ввод целого числа
  private static int safeInputInt (Scanner sc) {
	while (true) {
	  try {
		return sc.nextInt ();
	  } catch (InputMismatchException e) {
		System.out.println ("Ошибка ввода. Введите целое число.");
		sc.nextLine ();
	  }
	}
  }

  // Безопасный ввод числа с плавающей точкой
  private static double safeInputDouble (Scanner sc) {
	while (true) {
	  try {
		return sc.nextDouble ();
	  } catch (InputMismatchException e) {
		System.out.println ("Ошибка ввода. Введите правильное число.");
		sc.nextLine ();
	  }
	}
  }


  public static double calcFloorArea (double length, double width) {
	return length * width;
  }

  public static double calcWallArea (double length, double width, double height) {
	return 2 * (length * height) + 2 * (width * height);
  }

  public static double calcRoomPerimeter (double length, double width) {
	return 2 * (length + width);
  }

  public static double calculateCableLengthInsideRoom (double roomPerimeter, int sockets, double distanceBetweenRooms) {
	return (sockets * (roomPerimeter / ELECTRICITY_FACTOR) + distanceBetweenRooms);
  }

  public static double calcWaterPipeLength (double roomPerimeter, double sockets) {
	return roomPerimeter / WATER_FACTOR * sockets;
  }

  public static double calcOpeningPerimeter (double openingWidth, double openingHeight) {
	return 2 * (openingWidth + openingHeight);
  }

  public static double calcOpeningArea (double openingWidth, double openingHeight) {
	return openingWidth * openingHeight;
  }

  public static double calcCircleArea (double radius) {
	return Math.PI * radius * radius;
  }

  // Метод для расчета площади треугольника
  public static double calcTriangleArea (double base, double height) {
	return (base * height) / 2;
  }

  public static double calcNonStandardFloorArea (Scanner sc) {
	double totalArea = 0;
	System.out.println ("Введите количество зон:");
	int zoneCount = sc.nextInt ();
	for (int i = 1; i <= zoneCount; i++) {

	  System.out.println ("Введите тип зоны " + i + " (1 - прямоугольник, 2 - треугольник, 3 - круг):");
	  int zoneType = sc.nextInt ();

	  switch (zoneType) {
		case 1: // Прямоугольник
		  System.out.println ("Введите длину прямоугольника:");
		  double rectLength = sc.nextDouble ();
		  System.out.println ("Введите ширину прямоугольника:");
		  double rectWidth = sc.nextDouble ();
		  //Площадь прямоугольника totalArea += calcRectangleArea(rectLength, rectWidth)
		  totalArea += calcFloorArea (rectLength, rectWidth); // Используем существующий метод
		  break;

		case 2: // Треугольник
		  System.out.println ("Введите основание треугольника:");
		  double triangleBase = sc.nextDouble ();
		  System.out.println ("Введите высоту треугольника:");
		  double triangleHeight = sc.nextDouble ();
		  totalArea += calcTriangleArea (triangleBase, triangleHeight); // Новый метод для треугольника
		  break;

		case 3: // Круг
		  System.out.println ("Введите радиус круга:");
		  double radius = sc.nextDouble ();
		  totalArea += calcCircleArea (radius); // Новый метод для круга
		  break;

		default:
		  System.out.println ("Неизвестный тип зоны. " +
								  "Попробуйте снова.");
		  //i--;// Заглушка, чтобы не засчитывать ввод как
		  // "целую зону"
	  }
	}
	System.out.println ("Общая площадь нестандартного помещения: " + totalArea + " м².");
	return totalArea;
  }

  private static void printRoomDetails ( PrintWriter writer, String roomName, double floorArea, double wallArea,
										 double roomPerimeter, double roomWindowsArea, double roomWindowsSlopeLength,
										 double roomDoorsArea, double roomDoorsSlopeLength, int roomSwitches, double roomCableSwitches, int roomLamps, double roomCableLamps,  int roomNumberSockets,
										 double cableLengthInsideRoom, double roomWaterPipeLength, double roomWaterSockets)
  {

	writer.println ("КОМНАТА: : " + roomName );
	writer.println ("Общая площадь пола: " + floorArea + " м²");
	writer.println ("Общая площадь стен: " + wallArea + " м²");
	writer.println ("Общий периметр квартиры: " + roomPerimeter + " м");
	writer.println ("Общая площадь окон: " + roomWindowsArea + " м²");
	writer.println ("Общая длина откосов окон: " + roomWindowsSlopeLength + " м");
	writer.println ("Общая площадь дверей: " + roomDoorsArea + " м²");
	writer.println ("Общая длина откосов дверей: " + roomDoorsSlopeLength + " м");
	writer.println ("Общее количество выключателей: " + roomSwitches);
	writer.println ("Общая длина кабеля до выключателей: " + roomCableSwitches+ " м");
	writer.println ("Общее количество светильников: " + roomLamps);
	writer.println ("Общая длина кабеля до светильников: " + roomCableLamps + " м");
	writer.println ("Общее количество розеток: " + roomNumberSockets);
	writer.println ("Общая длина кабеля для всех комнат: " + cableLengthInsideRoom + " м");
	writer.println ("Общее количество водорозеток: " + roomWaterSockets);
	writer.println ("Общая длина труб: " + roomWaterPipeLength + " м");
	writer.println ();
  }

  private static void printApartmentDetails (PrintWriter writer, String apartmentName, double totalApartmentArea, double totalFloorArea,
											  double totalWallArea, double totalApartmentPerimeter, double totalWindowsArea,
											  double totalWindowsSlopeLength, double totalDoorsArea,
											  double totalDoorsSlopeLength, int totalSwitches, double totalCableSwitches, int totalLamps, double totalCableLamps, int totalNumberOfSockets,
											  double totalCableLength, double totalWaterSockets, double totalWaterPipeLength)
  {
	writer.println (" КВАРТИРА: " + apartmentName );
	writer.println ("Общая площадь квартиры: " + totalApartmentArea + " м²");
	writer.println ("Общая площадь пола: " + totalFloorArea + " м²");
	writer.println ("Общая площадь стен: " + totalWallArea + " м²");
	writer.println ("Общий периметр квартиры: " + totalApartmentPerimeter + " м");
	writer.println ("Общая площадь окон: " + totalWindowsArea + " м²");
	writer.println ("Общая длина откосов окон: " + totalWindowsSlopeLength + " м");
	writer.println ("Общая площадь дверей: " + totalDoorsArea + " м²");
	writer.println ("Общая длина откосов дверей: " + totalDoorsSlopeLength + " м");
	writer.println ("Общее количество выключателей: " + totalSwitches);
	writer.println ("Общая длина кабеля до выключателей: " + totalCableSwitches + " м");
	writer.println ("Общее количество светильников: " + totalLamps);
	writer.println ("Общая длина кабеля до светильников: " + totalCableLamps + " м");
	writer.println ("Общее количество розеток: " + totalNumberOfSockets);
	writer.println ("Общая длина кабеля для всех комнат: " + totalCableLength + " м");
	writer.println ("Общее количество водорозеток: " + totalWaterSockets);
	writer.println ("Общая длина труб: " + totalWaterPipeLength + " м");
	writer.println ();
  }
}
