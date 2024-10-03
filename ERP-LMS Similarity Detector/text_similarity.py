import sys
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

def main():
    # Read text inputs from standard input
    text1 = sys.stdin.readline().strip()
    text2 = sys.stdin.readline().strip()

    # Create the Document Term Matrix using TF-IDF
    vectorizer = TfidfVectorizer()
    tfidf_matrix = vectorizer.fit_transform([text1, text2])

    # Calculate the cosine similarity between the two documents
    similarity_matrix = cosine_similarity(tfidf_matrix[0:1], tfidf_matrix[1:2])

    # Output the similarity as a percentage
    similarity_percentage = similarity_matrix[0][0] * 100
    print(f"{similarity_percentage:.2f}")  # Print the similarity percentage

if __name__ == "__main__":
    main()
