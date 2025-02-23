import openai
import os
import sys

# Set your OpenAI API key
openai.api_key = os.getenv('OPENAI_API_KEY')

def read_prompt():
    """Read the prompt template from the prompt.txt file."""
    try:
        with open('prompt.txt', 'r') as f:
            return f.read()
    except FileNotFoundError:
        return "Please review the following code and provide suggestions for improvement:\n\n{file_content}"

def review_code(file_path):
    """Function to get review comments for a file using OpenAI."""
    try:
        with open(file_path, 'r') as f:
            file_content = f.read()

        # Read the prompt from the prompt.txt file
        prompt = read_prompt()

        # Format the prompt with the file content
        formatted_prompt = prompt.format(file_content=file_content)

        # Request OpenAI to review the code
        response = openai.Completion.create(
            model="text-davinci-003",  # You can change the model if needed
            prompt=formatted_prompt,
            max_tokens=1000
        )

        # Return the review comments from OpenAI
        return response.choices[0].text.strip()
    except Exception as e:
        return f"Error reviewing file {file_path}: {str(e)}"

def main():
    # Get the list of files from the command-line arguments
    changed_files = sys.argv[1:]

    # Prepare to store all review comments
    review_comments = []

    # Review each changed file
    for file in changed_files:
        print(f"Reviewing file: {file}")
        review = review_code(file)
        review_comments.append(f"### Review for {file}:\n\n{review}\n")

    # Join and print the comments for all files
    review_result = "\n".join(review_comments)
    print(review_result)

    # Output the review result (GitHub Actions can use this output)
    print(f"::set-output name=review_result::{review_result}")

if __name__ == "__main__":
    main()
