package com.wgapp.worksheetgenerator.Utils;

public class PromtConstants {

    public static final String PROMPT_BEGINNING_COMPREHENSION = """
            Create a comprehension worksheet based on the provided passage. The worksheet should have exactly 5 questions, focusing on the following types specified by the user:
            Use the following format for the questions:
            1. [Question text] ([Question type]) ([Correct Option: X)]
            2. [Question text] ([Question type]) ([Correct Option: X)]
            
            Examples of question structures:
            - Question 1: What does the term "quagmire" mean in the passage? (VOCABULARY)(Correct Option: A)
              A.[Option Text]
              B.[Option Text]
              C.[Option Text]
              D.[Option Text]
            - Question 2: Why did Ferdinand Magellan offer to serve the future Emperor Charles V of Spain? (CHARACTER ANALYSIS)(Correct Option: B)
              A.[Option Text]
              B.[Option Text]
              C.[Option Text]
              D.[Option Text]
            
            Rules:
            1. Ensure each question is directly related to the passage.
            2. Maintain a balance between difficulty levels across the questions.
            3. Format the output as a numbered list.
            User Options:
            """;


    public static final String PROMPT_BEGINNING_CLOZE = """
                Create a cloze test worksheet based on the provided passage. The worksheet should have exactly 5 questions, with a focus on vocabulary, grammar, or context-based knowledge. Follow these instructions carefully:
            
                **Instructions for the passage:**
                - Rewrite the passage by replacing 5 key words or phrases with blanks (denoted as `________`). 
                - Ensure the blanks test specific knowledge about word usage, grammar, or vocabulary in context.
                - Examples of sentences in the passage:
                  - "I like ________ in the kitchen." (Options: A. cooking, B. cleaning, C. walking, D. dancing)
                  - "The boy was ________ when he got home late." (Options: A. tired, B. excited, C. upset, D. happy)
            
                **Instructions for the questions:**
                - Each blank in the passage should correspond to a multiple-choice question.
                - The questions should ask for the best word or phrase to fit the blank, based on the context in the passage.
                - Each question should follow the structure below:
            
                **Question Format:**
                1. [Sentence with the blank as the question context] (Correct Option: [Correct Answer])
                   A. [Option Text]
                   B. [Option Text]
                   C. [Option Text]
                   D. [Option Text]
                2. [Sentence with the blank as the question context] (Correct Option: [Correct Answer])
                   A. [Option Text]
                   B. [Option Text]
                   C. [Option Text]
                   D. [Option Text]
            
                **Examples of question structures:**
                - Question 1: Which word fits best in the blank? "I like ________ in the kitchen." (Correct Option: A)
                  A. cooking
                  B. cleaning
                  C. walking
                  D. dancing
                - Question 2: Which word fits best in the blank? "The boy was ________ when he got home late." (Correct Option: C)
                  A. tired
                  B. excited
                  C. upset
                  D. happy
            
                **Rules:**
                1. Each blank should test the user's ability to understand the passage and choose the correct word or phrase.
                2. Questions should be based on everyday situations or common contexts (e.g., hobbies, emotions, actions, or descriptive words).
                3. Maintain a mix of easy, medium, and hard questions to test different levels of understanding.
                4. Ensure that the options are plausible but only one is correct based on the context.
            
                User Options:
            """;
    public static final String PROMPT_BEGINNING_VOCABULARY = """
                Create a vocabulary worksheet based on the provided passage. The worksheet should have exactly 5 questions, following these instructions:
            
                **Instructions for the passage:**
                - AI should process the passage and underline five key vocabulary words and put question related question number at the end of the word..
                - These words should be selected based on their contextual importance.
                - The selected words should be moderately challenging, encouraging vocabulary development.
            
                **Instructions for the questions:**
                - Each underlined word should have a corresponding multiple-choice question.
                - The question should ask which word can best replace the underlined word in the sentence while keeping the meaning intact.
                - The distractor choices should be plausible but not exact synonyms.
            
                **Question Format:**
                1. Which word can best replace the underlined word in the sentence? (Correct Option: [Correct Answer])
                   Sentence: "The scientist made a **crucial** discovery that changed history."
                   A. unimportant  
                   B. vital  
                   C. random  
                   D. delayed  
                2. Which word can best replace the underlined word in the sentence? (Correct Option: [Correct Answer])
                   Sentence: "The storm was so **ferocious** that it uprooted several trees."
                   A. gentle  
                   B. aggressive  
                   C. mild  
                   D. slow  
            
                **Rules:**
                1. Ensure each question is directly linked to the passage.
                2. The selected words should be commonly used but offer room for deeper understanding.
                3. Maintain a mix of difficulty levels across the five questions.
                4. Format the output as a numbered list.
            
                User Options:
            """;


}
