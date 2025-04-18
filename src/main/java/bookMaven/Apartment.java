package bookMaven;
/* https://github.com/MISHAM72/Apartment_Calc_Maven */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.jetbrains.annotations.NotNull;

public class Apartment {

    private static final double WATER_FACTOR = 1.1; // Константа для водопровода


    // @SuppressWarnings("unused")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите название вашей квартиры (например: Квартира №.....):");
        String apartmentName = sc.nextLine();

        try (PrintWriter writer = new PrintWriter(new FileWriter("result.txt", false))) {
            // Инициализация переменных для подсчета итогов
            double totalApartmentArea = 0;
            double totalWallArea = 0;
            double totalFloorArea = 0;
            double totalApartmentPerimeter = 0;
            double totalWindowsSlopeLength = 0;
            double totalDoorsSlopeLength = 0;
            double totalWindowsArea = 0;
            double totalDoorsArea = 0;
            int totalSockets = 0;
            double totalCableSockets = 0;
            int totalSwitches = 0;
            int totalLamps = 0;
            double totalCableLamps = 0;
            int totalWaterSockets = 0;
            double totalWaterPipeLength = 0;
            double totalPlasterVolume = 0;
            double totalFloorScreedVolume = 0;

            writer.println();
            writer.println("===== Новый запуск программы =====");
            writer.println();
            writer.println("Дата и время: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println();
            System.out.println("ВВЕДИТЕ КОЛИЧЕСТВО КОМНАТ В КВАТИРЕ: ");
            int roomCount = safeInputInt(sc);
            sc.nextLine();


            //-----КОМНАТА -------------------------------------------------------------------------------------------------
            for (int r = 1; r <= roomCount; r++) {
                System.out.println("КОМНАТА СТАНДАРТНАЯ (прямоугольная) или нет введите (ДА) или (НЕТ):");
                String isStandard = sc.nextLine();
                System.out.println("Введите название комнаты (например: Гостинная,Спальня, Коридор, Кухня, Ванна, Туалет):");
                String roomName = sc.nextLine();
                writer.println();
                double wallArea, floorArea, roomPerimeter;
                double length = 0;
                double width;
                double height;

                if (isStandard.equalsIgnoreCase("да")) {
                    // Прямоугольная комната
                    //здесь данные берем со сканера
                    System.out.println("Введите длину комнаты (м):");
                    length = safeInputDouble(sc);
                    sc.nextLine();
                    System.out.println("Введите ширину комнаты (м):");
                    width = safeInputDouble(sc);
                    sc.nextLine();
                    System.out.println("Введите высоту комнаты (м):");
                    height = safeInputDouble(sc);
                    sc.nextLine();
                    // а здесь вызываем методы и данные оттуда берутся
                    floorArea = calcFloorArea(length, width);
                    wallArea = calcWallArea(length, width, height);
                    roomPerimeter = calcRoomPerimeter(length, width);

                } else {

                    System.out.println("Введите высоту стен (м):");
                    height = safeInputDouble(sc);
                    sc.nextLine();
                    System.out.println("Введите суммарный периметр нестандартного помещения:");
                    roomPerimeter = safeInputDouble(sc);
                    sc.nextLine();
                    //а здесь мы сразу расчет сделали
                    wallArea = roomPerimeter * height;
                    // а здесь вызываем методы и данные оттуда берутся
                    floorArea = calcNonStandardFloorArea(sc);
                }
                // БЛОК ВВОДА ТОЛЩИНЫ СТЯЖКИ И РАСЧЕТА ЕЕ ОБЪЕМА
                System.out.println("Введите толщину стяжки в - " + roomName + " в м.кв.");
                double screedThickness = safeInputDouble(sc);
                double roomScreedVolume = calcScreedVolume(floorArea, screedThickness);
                if (roomScreedVolume > 0) {
                    System.out.println("Объем стяжки для комнаты  - " + roomName + ": " + roomScreedVolume + " м³");

                }


                // ОКНА
                double roomWindowsArea = 0;
                double roomWindowsSlopeLength = 0;
                double openingWidth;
                double openingHeight;
                System.out.println("Введите количество окон:");
                int windowsCount = safeInputInt(sc);
                sc.nextLine();
                for (int w = 1; w <= windowsCount; w++) {
                    System.out.println("Введите ширину окна " + w + " (в метрах):");
                    openingWidth = safeInputDouble(sc);
                    sc.nextLine();
                    System.out.println("Введите высоту окна " + w + " (в метрах):");
                    openingHeight = safeInputDouble(sc);
                    sc.nextLine();
                    roomWindowsArea += calcOpeningArea(openingWidth, openingHeight);
                    roomWindowsSlopeLength += calcOpeningPerimeter(openingWidth, openingHeight);
                }

                // ДВЕРИ
                double roomDoorsArea = 0;
                double roomDoorsSlopeLength = 0;
                System.out.println("Введите количество дверей:");
                int doorsCount = safeInputInt(sc);
                sc.nextLine();
                for (int d = 1; d <= doorsCount; d++) {
                    System.out.println("Введите ширину двери " + d + " (в метрах):");
                    openingWidth = safeInputDouble(sc);
                    sc.nextLine();
                    System.out.println("Введите высоту двери " + d + " (в метрах):");
                    openingHeight = safeInputDouble(sc);
                    sc.nextLine();
                    roomDoorsArea += calcOpeningArea(openingWidth, openingHeight);
                    roomDoorsSlopeLength += calcOpeningPerimeter(openingWidth, openingHeight);
                }

                double finalWallArea = wallArea - roomWindowsArea - roomDoorsArea;

                System.out.println("введите толщину слоя штукатурки комнаты " + roomName + " ( в метрах)");
                double plasterThickness = safeInputDouble(sc);
                double roomPlasterVolume = calcPlasterVolume(finalWallArea, plasterThickness);
                System.out.println("Объем штукатурки для комнаты - " + roomName + " : " + roomPlasterVolume + " м.кв.");

                //ЭЛЕКТРИКА
                int switches;
                int lamps;
                double cableLamps;
                double distanceBetweenRooms;
                double cableSockets;
                int sockets;
                double heightSockets;
                double heightSwitches;
                double heightLamps;

                System.out.println("введите кол-во выключателей (шт.): ");
                switches = safeInputInt(sc);
                sc.nextLine();
                System.out.println("введите кол-во светильников (шт.): ");
                lamps = safeInputInt(sc);
                sc.nextLine();
                System.out.println("Введите количество розеток (шт.):");
                sockets = safeInputInt(sc);
                sc.nextLine();
                System.out.println("Введите расстояние от щитка до комнаты (м):");
                distanceBetweenRooms = safeInputDouble(sc);
                sc.nextLine();
                System.out.println("Введите длину кабеля от пола до розетки с учетом выпуска (м.п.)");
                heightSockets = safeInputDouble(sc);
                sc.nextLine();
                System.out.println(" Введите длину кабеля от выключателя до потолка ( м.п.)");
                heightSwitches = safeInputDouble(sc);
                sc.nextLine();
                System.out.println(" Введите длину кабеля от потолка до лампы (м.п.)");
                heightLamps = safeInputDouble(sc);
                sc.nextLine();
                cableSockets = calcCableSockets(sockets, distanceBetweenRooms, isStandard.equalsIgnoreCase("да") ? length : 0, heightSockets);
                cableLamps = calcCableLamps(lamps, distanceBetweenRooms, isStandard.equalsIgnoreCase("да") ? length : 0, heightLamps, heightSwitches);

                //ВОДА
                int roomWaterSockets = 0;
                double roomWaterPipeLength = 0;
                if (roomName.equals("Кухня") || roomName.equals("Ванна") || roomName.equals("Туалет")) {
                    System.out.println("ведите кол-во водорозеток:");
                    roomWaterSockets = safeInputInt(sc);
                    sc.nextLine();
                    roomWaterPipeLength = calcWaterPipeLength(roomPerimeter, roomWaterSockets);
                }
                // Итоги по комнате
                printRoomDetails(writer, roomName, floorArea, finalWallArea, roomPerimeter, roomWindowsArea, roomWindowsSlopeLength,
                        roomDoorsArea, roomDoorsSlopeLength, roomPlasterVolume, lamps, cableLamps, switches,
                        sockets, cableSockets, roomWaterSockets, roomWaterPipeLength, roomScreedVolume);


                // Суммируем общие данные
                totalApartmentArea += floorArea;
                totalFloorArea += floorArea;
                totalWallArea += finalWallArea;
                totalApartmentPerimeter += roomPerimeter;
                totalWindowsArea += roomWindowsArea;
                totalWindowsSlopeLength += roomWindowsSlopeLength;
                totalDoorsArea += roomDoorsArea;
                totalDoorsSlopeLength += roomDoorsSlopeLength;
                totalPlasterVolume += roomPlasterVolume;
                totalSwitches += switches;
                totalLamps += lamps;
                totalCableLamps += cableLamps;
                totalSockets += sockets;
                totalCableSockets += cableSockets;
                totalWaterSockets += roomWaterSockets;
                totalWaterPipeLength += roomWaterPipeLength;
                totalFloorScreedVolume += roomScreedVolume;
            }
            // Итоговый отчет
            printApartmentDetails(writer, apartmentName, totalApartmentArea, totalFloorArea, totalWallArea, totalApartmentPerimeter,
                    totalWindowsArea, totalWindowsSlopeLength, totalDoorsArea, totalDoorsSlopeLength, totalPlasterVolume,
                    totalLamps, totalCableLamps, totalSwitches,   totalSockets, totalCableSockets, totalWaterSockets, totalWaterPipeLength,
                    totalFloorScreedVolume);


        } catch (IOException e) {
            System.out.println("ошибка при записи в файл" + e.getMessage());
        }

        sc.close();
    }


    // Безопасный ввод целого числа
    private static int safeInputInt(@NotNull Scanner sc) {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Введите целое число.");
                sc.nextLine();
            }
        }
    }

    // Безопасный ввод числа с плавающей точкой
    private static double safeInputDouble(@NotNull Scanner sc) {
        while (true) {
            try {
                return sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Введите правильное число.");
                sc.nextLine();
            }
        }
    }

    public static double calcFloorArea(double length, double width) {
        return length * width;
    }

    public static double calcWallArea(double length, double width, double height) {
        return 2 * (length * height) + 2 * (width * height);
    }

    public static double calcRoomPerimeter(double length, double width) {
        return 2 * (length + width);
    }

    public static double calcCableSockets(int sockets, double distanceBetweenRooms, double length, double heightSockets) {
        return sockets * (distanceBetweenRooms + length + heightSockets);
    }

    public static double calcCableLamps(int lamps, double distanceBetweenRooms, double length, double heightLamps, double heightSwitches) {
        return lamps * (distanceBetweenRooms + length + heightLamps + heightSwitches);
    }

    public static double calcWaterPipeLength(double roomPerimeter, int sockets) {
        return roomPerimeter / WATER_FACTOR * sockets;
    }

    public static double calcOpeningPerimeter(double openingWidth, double openingHeight) {
        return 2 * (openingWidth + openingHeight);
    }

    public static double calcOpeningArea(double openingWidth, double openingHeight) {
        return openingWidth * openingHeight;
    }

    public static double calcCircleArea(double radius) {
        return Math.PI * radius * radius;
    }

    // Метод для расчета площади треугольника
    public static double calcTriangleArea(double base, double height) {
        return (base * height) / 2;
    }

    public static double calcNonStandardFloorArea(@NotNull Scanner sc) {
        double totalArea = 0;
        System.out.println("Введите количество зон:");
        int zoneCount = sc.nextInt();
        for (int i = 1; i <= zoneCount; i++) {

            System.out.println("Введите тип зоны " + i + " (1 - прямоугольник, 2 - треугольник, 3 - круг):");
            int zoneType = sc.nextInt();

            switch (zoneType) {
                case 1: // Прямоугольник
                    System.out.println("Введите длину прямоугольника:");
                    double rectLength = sc.nextDouble();
                    System.out.println("Введите ширину прямоугольника:");
                    double rectWidth = sc.nextDouble();
                    //Площадь прямоугольника totalArea += calcRectangleArea(rectLength, rectWidth)
                    totalArea += calcFloorArea(rectLength, rectWidth); // Используем существующий метод
                    break;

                case 2: // Треугольник
                    System.out.println("Введите основание треугольника:");
                    double triangleBase = sc.nextDouble();
                    System.out.println("Введите высоту треугольника:");
                    double triangleHeight = sc.nextDouble();
                    totalArea += calcTriangleArea(triangleBase, triangleHeight); // Новый метод для треугольника
                    break;

                case 3: // Круг
                    System.out.println("Введите радиус круга:");
                    double radius = sc.nextDouble();
                    totalArea += calcCircleArea(radius); // Новый метод для круга
                    break;

                default:
                    System.out.println("Неизвестный тип зоны. " + "Попробуйте снова.");
                    //i--;// Заглушка, чтобы не засчитывать ввод как
                    // "целую зону"
            }
        }
        System.out.println("Общая площадь нестандартного помещения: " + totalArea + " м².");
        return totalArea;
    }

    public static double calcScreedVolume(double floorArea, double screedThickness) {
        return floorArea * screedThickness;
    }

    public static double calcPlasterVolume(double finalWallArea, double thickness) {
        return finalWallArea * thickness;
    }

    private static void printRoomDetails(@NotNull PrintWriter writer, String roomName, double floorArea, double finalWallArea, double roomPerimeter,
                                         double roomWindowsArea, double roomWindowsSlopeLength, double roomDoorsArea, double roomDoorsSlopeLength,
                                         double roomPlasterVolume, int lamps, double cableLamps, int switches, int sockets, double cableSockets, double roomWaterSockets,
                                         double roomWaterPipeLength, double roomScreedVolume) {
        writer.println();
        writer.println("КОМНАТА: : " + roomName);
        writer.println("Общая площадь пола: " + floorArea + " м²");
        writer.println("Площадь стен в комнате: " + finalWallArea + " м²");
        writer.println("Общий периметр квартиры: " + roomPerimeter + " м");
        writer.println("Общая площадь окон: " + roomWindowsArea + " м²");
        writer.println("Общая длина откосов окон: " + roomWindowsSlopeLength + " м");
        writer.println("Общая площадь дверей: " + roomDoorsArea + " м²");
        writer.println("Общая длина откосов дверей: " + roomDoorsSlopeLength + " м");
        writer.println("=====Объем штукатурки в комнате:" + roomPlasterVolume + "м.куб.");
        writer.println("Общее количество светильников: " + lamps);
        writer.println("=====Общая длина кабеля до светильников: " + cableLamps + " м");
        writer.println("Общее количество выключателей: " + switches);
        writer.println("Розетки в комнате 220 В. : " + sockets);
        writer.println("=====Длина кабеля (щиток - розетки) : " + cableSockets + " м");
        writer.println(" Водорозетки в комнате: " + roomWaterSockets);
        writer.println("=====Общая длина труб: " + roomWaterPipeLength + " м");
        writer.println("=====Оьъем стяжки по полу в комнате - " + roomScreedVolume + "м. куб.");
    }

    private static void printApartmentDetails(@NotNull PrintWriter writer, String apartmentName, double totalApartmentArea, double totalFloorArea,
                                              double totalWallArea, double totalApartmentPerimeter, double totalWindowsArea, double totalWindowsSlopeLength,
                                              double totalDoorsArea, double totalDoorsSlopeLength, double totalPlasterVolume, int totalLamps, double totalCableLamps,
                                              int totalSwitches, int totalSockets, double totalCableSockets, int totalWaterSockets, double totalWaterPipeLength,
                                              double totalFloorScreedVolume) {

        writer.println();
        writer.println(" КВАРТИРА: " + apartmentName);
        writer.println("Общая площадь квартиры: " + totalApartmentArea + " м²");
        writer.println("Общая площадь пола: " + totalFloorArea + " м²");
        writer.println("Общая площадь стен: " + totalWallArea + " м²");
        writer.println("Общий периметр квартиры: " + totalApartmentPerimeter + " м");
        writer.println("Общая площадь окон: " + totalWindowsArea + " м²");
        writer.println("Общая длина откосов окон: " + totalWindowsSlopeLength + " м");
        writer.println("Общая площадь дверей: " + totalDoorsArea + " м²");
        writer.println("Общая длина откосов дверей: " + totalDoorsSlopeLength + " м");
        writer.println("=====Объем штукатурки в квартире - " + totalPlasterVolume + " м. куб.");
        writer.println("Общее количество светильников: " + totalLamps);
        writer.println("=====Общая длина кабеля до светильников: " + totalCableLamps + " м");
        writer.println("Общее количество выключателей: " + totalSwitches);
        writer.println("Общее количество розеток: " + totalSockets);
        writer.println("=====Общая длина кабеля для всех комнат: " + totalCableSockets + " м");
        writer.println("Общее количество водорозеток: " + totalWaterSockets);
        writer.println("=====Общая длина труб: " + totalWaterPipeLength + " м");
        writer.println("=====Общий объем стяжки в квартире - " + totalFloorScreedVolume + " м. куб.");
    }


}



