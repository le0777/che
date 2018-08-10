/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.ide.api.filetypes;

import java.util.List;
import java.util.Set;
import org.eclipse.che.ide.api.resources.VirtualFile;

/**
 * Registry allows to register new {@link FileType} and get the registered one.
 *
 * @author Artem Zatsarynnyi
 */
public interface FileTypeRegistry {

  /**
   * Registers the specified File Type when {@link Collision} is not detected.
   *
   * <p>Use {@link Registration} to check if {@code fileType} was successfully registered.
   *
   * <p>Use {@link Collision} to get more more info about the cause of fail registration or ability
   * to merge {@link FileType} with compatible types when it is possible.
   *
   * @param fileType file type to register
   * @return result of File Type registration
   */
  Registration register(FileType fileType);

  /** Returns the set of all registered file types. */
  Set<FileType> getFileTypes();

  /**
   * Registers the specified File Type when {@link Collision} is not detected.
   *
   * <p>Note: {@link IllegalStateException} will be thrown when given File Type can not be
   * registered, so when {@link Collision#canBeSafelyMerged()} is {@code false}.
   *
   * <p>Given File Type will be merged automatically with compatible types when it is possible, so
   * when {@link Collision#canBeSafelyMerged()} is {@code true}.
   *
   * @param fileType file type to register
   */
  void registerFileType(FileType fileType);

  /**
   * Returns the {@link List} of all registered file types.
   *
   * @return {@link List} of all registered file types
   * @deprecated use {@link #getFileTypes()} instead
   */
  List<FileType> getRegisteredFileTypes();

  /**
   * Returns the file type of the specified file.
   *
   * @param file file for which type need to find
   * @return file type or default file type if no file type found
   */
  FileType getFileTypeByFile(VirtualFile file);

  /**
   * Returns the file type for the specified file extension.
   *
   * @param extension extension for which file type need to find
   * @return file type or default file type if no file type found
   */
  FileType getFileTypeByExtension(String extension);

  /**
   * Returns the file type which pattern matches the specified file name.
   *
   * @param name file name
   * @return file type or default file type if no file type's name pattern matches the given file
   *     name
   */
  FileType getFileTypeByFileName(String name);

  /**
   * Describes result of File Type registration
   *
   * @see FileTypeRegistry#register(FileType)
   */
  interface Registration {

    /**
     * Returns {@code true} when {@link FileType} was successfully registered and {@code false}
     * otherwise
     *
     * @see #getCollision()
     */
    boolean isSuccessful();

    /**
     * Returns collision at File Type registration. Can be used to get more info about the cause of
     * fail registration or ability to merge {@link FileType} with compatible types from {@link
     * FileTypeRegistry} when it is possible
     */
    Collision getCollision();
  }

  /**
   * Describes collision at File Type registration. Contains info about:
   *
   * <ul>
   *   *
   *   <li><code>mergeable types</code> - types which can be merged with candidate
   *   <li><code>unmergeable types</code> - incompatible types(have the same extension) which can
   *       not be merged with candidate
   * </ul>
   */
  interface Collision {

    /** Returns candidate for File Type registration */
    FileType getCandidate();

    /**
     * Returns {@code true } when {@link FileTypeRegistry} does not contain incompatible types and
     * contains types which can be merged with candidate by {@link #merge()} and {@code false}
     * otherwise
     *
     * @see #getMergeableTypes()
     * @see #merge()
     */
    boolean canBeSafelyMerged();

    /**
     * Returns types from {@link FileTypeRegistry} which can be merged with candidate or empty set
     * when {@link FileTypeRegistry} does not contain such types
     *
     * @see #canBeSafelyMerged()
     * @see #merge()
     */
    Set<FileType> getMergeableTypes();

    /**
     * Returns incompatible types from {@link FileTypeRegistry} which can not be merged with
     * candidate or empty set when {@link FileTypeRegistry} does not contain such types
     *
     * @see #canBeSafelyMerged()
     */
    Set<FileType> getUnmergeableTypes();

    /**
     * Merges candidate with registered types from {@link FileTypeRegistry}
     *
     * <p>Note: original File Type (candidate to merge) can be lost as object in consequence of
     * merge operation, so use set of merged types instead.
     *
     * @return set of merged types or empty set when {@link FileTypeRegistry} does not contain types
     *     to merge
     * @see #canBeSafelyMerged()
     * @see #getMergeableTypes()
     */
    Set<FileType> merge();
  }
}
