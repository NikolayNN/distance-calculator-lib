/**
 * Provides classes and interfaces for tracking and classifying distances based on vehicle speed and location data.
 *
 * <p>This package includes essential tools for managing distance data obtained via GPS and odometers,
 * categorizing them into urban and country based on predefined speed thresholds. It aims to aid in applications such
 * as fleet management, logistics, and transport analytics, where precise distance tracking and classification
 * are crucial for operational efficiency and data analysis.</p>
 *
 * <h2>Package Contents</h2>
 * <ul>
 *     <li>{@link by.nhorushko.classifieddistance.Speedable} - An interface defining the ability to retrieve speed data.</li>
 *     <li>{@link by.nhorushko.classifieddistance.SpeedDistanceable} - Extends {@code Speedable} to include methods for retrieving both GPS
 *         and odometer-based distances.</li>
 *     <li>{@link by.nhorushko.classifieddistance.Distance} - Represents both relative and absolute distance measurements.</li>
 *     <li>{@link by.nhorushko.classifieddistance.ClassifiedDistance} - Manages distances categorized into urban and country classes, with methods
 *         for aggregation and total distance calculations.</li>
 *     <li>{@link by.nhorushko.classifieddistance.ClassifiedDistanceStorage} - Aggregates and manages multiple instances of {@code ClassifiedDistance}.</li>
 * </ul>
 *
 * <p>This package is designed to integrate smoothly with systems that require a high level of detail and precision in
 * distance data processing and reporting, providing robust tools for effective data handling and analysis.</p>
 */
package by.nhorushko.classifieddistance;
