/**
 * Represents a matrix of numbers with separators as defined by the task specification.
 * Matrix is stored as a two dimensional array containing {@link Item} instances.
 * Array indices correspond to item positions in the matrix for easy access during data processing.
 *
 * @typedef {Array.<Item[]>} Matrix
 */

/**
 * Represents a single matrix item containing a number and separator.
 * Separator is stored in order to print output the same way as input.
 *
 * @typedef {Object} Item
 * @property {number} value Number.
 * @property {string} separator Separator - a space or a comma.
 */

/**
 * Parses the input text according to the format defined in task specification and returns a {@link Matrix} instance.
 *
 * @param {string} text Input text to parse.
 * @returns {Matrix} {@link Matrix} instance
 */
var parseInput = function (text) {
  var data = []; // Matrix instance

  // Split text into lines (according to task specification we assume that it's always \n, so we ignore \n\r line endings)
  text.split('\n').forEach(function (line) {
    if (!line) {
      // Skip empty lines (e.g. newline at end of the file)
      return;
    }
    // Execute a regular expression to match all values (with separators!) in a single line.
    // According to task specification we assume that each line has a trailing separator.
    var row = [];
    var expr = /(\-?\d+)(\s|,)/g
    while (m = expr.exec(line)) {
      // Construct Item instance and push it to the current row
      row.push({
        value: parseInt(m[1]),
        separator: m[2]
      });
    }
    // Add row to the matrix
    data.push(row);
  });

  return data;
}

/**
 * Performs a simple average-of-nearest-neighbors interpolation to determine the value of an item in a matrix.
 * Note that "bad" neighbors (zeros) are not considered when interpolating.
 * Function does not modify the given matrix, only returns a result of the calculation.
 *
 * @param {Matrix} data Matrix instance to use.
 * @param {number} x X coordinate of the item within the matrix (0-based).
 * @param {number} y Y coordinate of the item within the matrix (0-based).
 * @returns {number} Result of the interpolation.
 */
var interpolate = function (data, x, y) {
  var values = []; // Holds Item instances of neighbors (or undefined if neighbor does not exist)

  // Gather all neighbors

  values.push(data[y][x - 1]); // Left neighbor
  values.push(data[y][x + 1]); // Right neighbor
  if (data[y - 1]) {
    // Top neighbor
    values.push(data[y - 1][x]);
  }
  if (data[y + 1]) {
    // Bottom neighbor
    values.push(data[y + 1][x]);
  }

  // Now calculate the average

  var count = 0;
  var sum = 0;
  values.forEach(function (item) {
    // Only count "good" neighbors, skip undefineds and bad values (0)
    if (item && item.value !== 0) {
      count += 1;
      sum += item.value;
    }
  });
  return Math.round(sum / count);
}

/**
 * Processes given Matrix instance according to task specification - performs interpolation for every item with value 0.
 * Note that this function works in-place - modifies the given matrix.
 *
 * @param {Matrix} data {@link Matrix} instance to process
 */
var process = function (data) {
  for (var i = 0; i < data.length; i++) {
    var row = data[i];
    for (var j = 0; j < row.length; j++) {
      if (row[j].value === 0) {
        row[j].value = interpolate(data, j, i);
      }
    }
  }
}

/**
 * Prints given matrix to a string.
 *
 * @param {Matrix} data @{link Matrix} instance to print.
 * @returns {string} String representing the matrix; includes newlines ('\n') after every row
    and every item is followed by its specific separator.
 */
var printMatrix = function (data) {
  var result = '';
  data.forEach(function (row) {
    row.forEach(function (item) {
      result += item.value + item.separator;
    });
    result += '\n';
  });
  return result;
}

/**
 * Reads contents of a file and calls back with the result.
 * Note: this method does the work asynchronously and will return immediately.
 *
 * @param {File} f - instace of File class to read from
 * @param {function} callback - function accepting file contents as first parameter, will be called when contents are available
 */
var readTextFromFile = function (f, callback) {
  var reader = new FileReader();
  reader.onload = function(e) { 
    callback(e.target.result);
  }
  reader.readAsText(f);
}

// This function is called whenever file input changes (user chooses a file)
var onFileSelected = function (fileInput) { 
  var f = fileInput.files[0];

  // Accept only text files (CSV is text/csv)
  if (!f || !f.type.startsWith('text')) {
    alert('Please select a text file!');
    return;
  }

  readTextFromFile(f, function(text) {
    fileInput.value = ''; // Reset file input so the user does not have to reload the page to process another file
    document.getElementById('input').value = text;
    var data = parseInput(text);
    process(data);
    document.getElementById('output').value = printMatrix(data);
  });
};
